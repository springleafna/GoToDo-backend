package com.springleaf.gotodo.model.entity;

import lombok.Data;

/**
 * 任务组-任务分类-关联表实体类
 */
@Data
public class GroupCategory {
    /**
     * 任务组-任务类-关联ID
     */
    private Long groupTaskId;

    /**
     * 任务类ID
     */
    private Long categoryId;

    /**
     * 任务组ID
     */
    private Long groupId;

    /**
     * 任务类在组内的排序
     */
    private Integer sortOrder;
}
