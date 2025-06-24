package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.vo.CategoryVO;
import com.springleaf.gotodo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;

    /**
     * 获取任务分类列表
     */
    @GetMapping("/list")
    public Result<List<CategoryVO>> listCategory() {
        return categoryService.listCategory();
    }

    /**
     * 添加任务分类
     */
    @PostMapping("/save")
    public Result<Void> saveCategory(@RequestParam("categoryName") String categoryName) {
        return categoryService.saveCategory(categoryName);
    }

    /**
     * 将一个任务移动到某个任务分类下
     */
    @PutMapping("/move")
    public Result<Void> moveTaskToCategory(@RequestParam("taskId") Long TaskId, 
                                           @RequestParam("categoryId") Long categoryId) {
        return categoryService.moveTaskToCategory(TaskId, categoryId);
    }
}
