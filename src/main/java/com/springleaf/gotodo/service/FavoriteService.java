package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;

public interface FavoriteService {
    /**
     * 添加或取消收藏任务
     * @param taskId 任务ID
     * @param favorite 是否收藏
     * @return Result<Void>
     */
    Result<Void> addOrCancelFavorite(Long taskId, Boolean favorite);
}
