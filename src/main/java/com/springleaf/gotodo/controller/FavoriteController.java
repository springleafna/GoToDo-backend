package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {
    
    private final FavoriteService favoriteService;
    
    @PutMapping()
    public Result<Void> addFavorite(@RequestParam Long taskId, @RequestParam Boolean favorite) {
        return favoriteService.addOrCancelFavorite(taskId, favorite);
    }
}
