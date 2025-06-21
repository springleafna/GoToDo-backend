package com.springleaf.gotodo.model.entity;

import lombok.Data;

/**
 * 任务列表-任务-关联表实体类
 */
@Data
public class ListTask {
    /**
     * 任务列表-任务-关联ID
     */
    private Long listTaskId;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务列表ID
     */
    private Long listId;

    /**
     * 任务在任务列表内的排序
     */
    private Integer sortOrder;
}
