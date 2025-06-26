package com.springleaf.gotodo.job;

import com.springleaf.gotodo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskReminderScheduler {

    private final TaskService taskService;

    /**
     * 每隔一分钟执行一次
     */
    @Scheduled(cron = "0 * * * * ?", zone = "Asia/Shanghai")
    public void checkAndSendReminders() {
        try {
            log.info("---执行定时任务-任务提醒---");
            taskService.checkAndSendReminders();
        } catch (Exception e) {
            log.error("定时任务执行失败：任务提醒", e);
        }
    }
}
