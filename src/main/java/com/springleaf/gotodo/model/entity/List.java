package com.springleaf.gotodo.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务分类表实体类
 */
@Data
public class List {
    /**
     * 分类ID
     */
    private Long listId;

    /**
     * 分类名称
     */
    private String listName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记(0:正常 1:删除)
     */
    private Integer deleted;
}
