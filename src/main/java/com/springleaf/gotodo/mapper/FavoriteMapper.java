package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FavoriteMapper {
    /**
     * 任务收藏（第一次收藏时插入记录）
     */
    int saveFavorite(@Param("taskId") Long taskId, @Param("status") Integer status);

    /**
     * 更新任务收藏状态（收藏/取消收藏）
     */
    int updateFavoriteStatus(@Param("taskId") Long taskId, @Param("status") Integer status);

    /**
     * 获取收藏的任务数
     */
    int countFavorite();

    /**
     * 获取收藏实体
     */
    Favorite getFavoriteByTaskId(Long taskId);
}
