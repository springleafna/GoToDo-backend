package com.springleaf.gotodo.job;

import com.springleaf.gotodo.service.MemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每天定时清理空便签
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CleanEmptyMemoScheduler {
    
    private final MemoService memoService;

    /**
     * 每天凌晨 4:00:00 执行
     */
    @Scheduled(cron = "0 0 4 * * ?", zone = "Asia/Shanghai")
    public void cleanEmptyMemo() {
        try {
            log.info("---执行定时任务-清理空便签---");
            memoService.cleanEmptyMemo();
        } catch (Exception e) {
            log.error("定时任务执行失败：清理空便签", e);
        }
    }
}
