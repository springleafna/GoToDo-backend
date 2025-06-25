package com.springleaf.gotodo.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务视图对象
 */
@Data
public class TaskVO {
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
     * 完成状态(0:未完成 1:已完成)
     */
    private Integer completed;

    /**
     * 优先级(0:低 1:中 2:高)
     */
    private Integer priority;

    /**
     * 收藏状态(0:未收藏 1:已收藏)
     */
    private Integer favorite;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 排序
     */
    private Integer sortOrder;
}
