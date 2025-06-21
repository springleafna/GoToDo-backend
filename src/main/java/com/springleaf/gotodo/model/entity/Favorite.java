package com.springleaf.gotodo.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户收藏任务表实体类
 */
@Data
public class Favorite {
    /**
     * 收藏ID
     */
    private Long favoriteId;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 收藏状态(1:已收藏 0:已取消)
     */
    private Integer status;

    /**
     * 收藏时间
     */
    private LocalDateTime createTime;
}
