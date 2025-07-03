package com.springleaf.gotodo.feishu;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FeiShu {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private static final int MAX_RETRIES = 3;
    private static final String PATH = "FEISHU_WEBHOOK";
    private final String webhook;

    public FeiShu() {
        this.webhook = Objects.requireNonNull(System.getenv(PATH), "Webhook URL cannot be null");
        if (webhook.trim().isEmpty()) {
            throw new IllegalArgumentException("Webhook URL cannot be empty");
        }
        if (!webhook.startsWith("https://")) {
            throw new IllegalArgumentException("Webhook URL must start with https://");
        }
    }

    public void sendTemplateMessage(String title, String categoryName, String remark, String reminderTime, String endTime) throws IOException {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(categoryName, "CategoryName cannot be null");
        Objects.requireNonNull(reminderTime, "ReminderTime cannot be null");

        int attempt = 0;
        IOException lastException = null;

        while (attempt < MAX_RETRIES) {
            attempt++;
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(webhook).openConnection();
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setDoOutput(true);

                FeiShuTaskReminderCard message = new FeiShuTaskReminderCard(
                        title, categoryName, remark, reminderTime, endTime);

                try (OutputStream os = conn.getOutputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(os, message);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP request failed with code: " + responseCode);
                }

                try (InputStream is = conn.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    String response = reader.lines().collect(Collectors.joining());
                    log.info("Message sent successfully. Response: {}", response);
                }
                return;
            } catch (IOException e) {
                lastException = e;
                log.warn("Attempt {} failed: {}", attempt, e.getMessage());
                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(1000L * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Interrupted during retry", ie);
                    }
                }
            }
        }
        throw lastException;
    }
}
