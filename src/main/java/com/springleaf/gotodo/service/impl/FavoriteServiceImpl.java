package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.FavoriteStatusEnum;
import com.springleaf.gotodo.mapper.FavoriteMapper;
import com.springleaf.gotodo.mapper.TaskMapper;
import com.springleaf.gotodo.model.entity.Favorite;
import com.springleaf.gotodo.model.entity.Task;
import com.springleaf.gotodo.model.vo.TaskVO;
import com.springleaf.gotodo.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    
    private final FavoriteMapper favoriteMapper;
    private final TaskMapper taskMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> addOrCancelFavorite(Long taskId, Boolean favorite) {
        // 参数校验
        if (taskId == null) {
            return Result.error("任务ID不能为空");
        }
        if (favorite == null) {
            return Result.error("收藏状态不能为空");
        }

        // 校验任务是否存在
        if (taskMapper.getTaskByTaskId(taskId) == null) {
            return Result.error("任务不存在");
        }

        // 获取目标状态码
        Integer targetStatus = favorite ? FavoriteStatusEnum.FAVORITE.getCode() : FavoriteStatusEnum.CANCELLED.getCode();

        // 查询当前收藏状态
        Favorite currentFavorite = favoriteMapper.getFavoriteByTaskId(taskId);

        // 1. 当前没有收藏记录，需要添加
        if (currentFavorite == null) {
            if (favoriteMapper.saveFavorite(taskId, targetStatus) == 0) {
                return Result.error("任务收藏失败");
            }
            return Result.success();
        }

        // 2. 已有收藏记录，判断是否需要更新
        if (currentFavorite.getStatus().equals(targetStatus)) {
            return Result.success(); // 状态一致，无需操作
        }

        // 3. 状态不一致，进行更新
        if (favoriteMapper.updateFavoriteStatus(taskId, targetStatus) == 0) {
            return Result.error("任务收藏状态更新失败");
        }

        return Result.success();
    }

    @Override
    public Result<Integer> countFavorite() {
        return Result.success(favoriteMapper.countFavorite());
    }

    @Override
    public Result<List<TaskVO>> getFavoriteList() {
        // TODO
        List<Task> taskVOList = favoriteMapper.getFavoriteList();
        return null;
    }
}
