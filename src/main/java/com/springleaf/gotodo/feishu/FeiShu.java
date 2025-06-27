package com.springleaf.gotodo.feishu;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class FeiShu {
    private final Logger logger = LoggerFactory.getLogger(FeiShu.class);
    private final String webhook;

    public FeiShu(String webhook) {
        if (webhook == null || webhook.trim().isEmpty()) {
            throw new IllegalArgumentException("Webhook URL cannot be null or empty");
        }
        if (!webhook.startsWith("https://")) {
            throw new IllegalArgumentException("Invalid webhook URL format");
        }
        this.webhook = webhook;
    }

    public void sendTemplateMessage(String title, String categoryName, String remark, String reminderTime, String endTime) throws IOException {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(categoryName, "CategoryName cannot be null");
        Objects.requireNonNull(reminderTime, "ReminderTime cannot be null");

        URL url = new URL(webhook);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        FeiShuTaskReminderCard message = new FeiShuTaskReminderCard(title, categoryName, remark, reminderTime, endTime);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = JSON.toJSONString(message).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to send FeiShu message. Response code: " + responseCode);
        }

        try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
            String response = scanner.useDelimiter("\\A").next();
            logger.info("FeiShu message sent successfully. Response: {}", response);
        }
    }
}
