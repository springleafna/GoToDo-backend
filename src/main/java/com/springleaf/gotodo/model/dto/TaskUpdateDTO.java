package com.springleaf.gotodo.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 任务更新DTO
 */
@Getter
public class TaskUpdateDTO {

    /**
     * 任务分类ID
     */
    private Long categoryId;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务备注
     */
    private String remark;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 提醒时间
     */
    private LocalDateTime reminderTime;

    /**
     * 完成状态(0:未完成 1:已完成)
     */
    private Integer completed;

    /**
     * 优先级(0:低 1:中 2:高)
     */
    private Integer priority;
    
    /**
     * 删除标记(0:正常 1:删除)
     */
    private Integer deleted;
}
