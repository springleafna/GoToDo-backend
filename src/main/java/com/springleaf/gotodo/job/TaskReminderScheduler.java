package com.springleaf.gotodo.job;

import com.springleaf.gotodo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskReminderScheduler {

    private final TaskService taskService;

    /**
     * 每隔一分钟执行一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkAndSendReminders() {
        taskService.checkAndSendReminders();
    }
}
