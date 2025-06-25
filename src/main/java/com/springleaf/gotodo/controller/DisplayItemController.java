package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.vo.DisplayItemVO;
import com.springleaf.gotodo.service.DisplayItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/display-item")
@RequiredArgsConstructor
public class DisplayItemController {
    
    private final DisplayItemService displayItemService;

    /**
     * 获取所有的展示项
     */
    @GetMapping("/list")
    public Result<List<DisplayItemVO>> listDisplayItem() {
        return displayItemService.listDisplayItem();
    }
}
