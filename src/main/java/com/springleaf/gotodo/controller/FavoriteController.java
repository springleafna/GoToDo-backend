package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.vo.TaskVO;
import com.springleaf.gotodo.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {
    
    private final FavoriteService favoriteService;

    /**
     * 收藏/取消收藏任务
     */
    @PutMapping()
    public Result<Void> addFavorite(@RequestParam Long taskId, @RequestParam Boolean favorite) {
        return favoriteService.addOrCancelFavorite(taskId, favorite);
    }

    /**
     * 获取收藏任务数
     */
    @GetMapping("/count")
    public Result<Integer> countFavorite() {
        return favoriteService.countFavorite();
    }

    /**
     * 获取收藏的任务列表
     */
    @GetMapping("/list")
    public Result<List<TaskVO>> getFavoriteList() {
        return favoriteService.getFavoriteList();
    }

}
