package com.springleaf.gotodo.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务组表实体类
 */
@Data
public class Group {
    /**
     * 任务组ID
     */
    private Long groupId;

    /**
     * 组名称
     */
    private String groupName;

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
