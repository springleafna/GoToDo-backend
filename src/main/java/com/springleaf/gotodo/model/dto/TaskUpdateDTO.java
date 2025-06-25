package com.springleaf.gotodo.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务更新DTO
 */
@Data
public class TaskUpdateDTO {

    /**
     * 任务ID
     */
    private Long taskId;

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
     * 优先级(0:低 1:中 2:高)
     */
    private Integer priority;

}
