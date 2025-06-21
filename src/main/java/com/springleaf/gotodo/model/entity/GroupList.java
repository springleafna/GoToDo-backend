package com.springleaf.gotodo.model.entity;

import lombok.Data;

/**
 * 任务组-任务列表-关联表实体类
 */
@Data
public class GroupList {
    /**
     * 任务组-任务列表-关联ID
     */
    private Long groupTaskId;

    /**
     * 任务列表ID
     */
    private Long listId;

    /**
     * 任务组ID
     */
    private Long groupId;

    /**
     * 任务列表在组内的排序
     */
    private Integer sortOrder;
}
