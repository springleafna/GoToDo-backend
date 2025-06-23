package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.vo.TaskVO;

import java.util.List;

public interface FavoriteService {
    /**
     * 添加或取消收藏任务
     * @param taskId 任务ID
     * @param favorite 是否收藏
     * @return Result<Void>
     */
    Result<Void> addOrCancelFavorite(Long taskId, Boolean favorite);

    /**
     * 统计收藏任务数量
     */
    Result<Integer> countFavorite();

    /**
     * 获取收藏任务列表
     * @return Result<List<TaskVO>>
     */
    Result<List<TaskVO>> getFavoriteList();
}
