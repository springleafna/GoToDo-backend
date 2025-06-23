package com.springleaf.gotodo.model.entity;

import lombok.Data;

/**
 * 任务类-任务-关联表实体类
 */
@Data
public class CategoryTask {
    /**
     * 任务类-任务-关联ID
     */
    private Long categoryTaskId;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务类ID
     */
    private Long categoryId;

    /**
     * 任务在任务分类内的排序
     */
    private Integer sortOrder;
}
