package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.DeletedStatusEnum;
import com.springleaf.gotodo.mapper.CategoryMapper;
import com.springleaf.gotodo.mapper.CategoryTaskMapper;
import com.springleaf.gotodo.mapper.TaskMapper;
import com.springleaf.gotodo.model.entity.Category;
import com.springleaf.gotodo.model.vo.CategoryVO;
import com.springleaf.gotodo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final TaskMapper taskMapper;
    private final CategoryTaskMapper categoryTaskMapper;

    @Override
    public Result<List<CategoryVO>> listCategory() {
        List<Category> categorieList = categoryMapper.listCategory();
        if (categorieList == null || categorieList.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        List<CategoryVO> categoryVOList = categorieList.stream()
                .map(this::convertToCategoryVO)
                .toList();
        return Result.success(categoryVOList);
    }

    @Override
    public Result<Void> saveCategory(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            return Result.error("分类名称不能为空");
        }
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setDeleted(DeletedStatusEnum.NORMAL.getCode());
        if (categoryMapper.saveCategory(category) == 0) {
            return Result.error("保存分类失败");
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Void> moveTaskToCategory(Long taskId, Long categoryId) {
        if (taskId == null) {
            return Result.error("任务ID不能为空");
        }
        if (categoryId == null) {
            return Result.error("分类ID不能为空");
        }
        if (taskMapper.getTaskByTaskId(taskId) == null) {
            return Result.error("任务不存在");
        }
        if (categoryMapper.getCategoryByCategoryId(categoryId) == null) {
            return Result.error("分类不存在");
        }
        // 1. 先删除原有的分类任务关系
        if (categoryTaskMapper.deleteTaskCategoryByTaskId(taskId) == 0) {
            return Result.error("删除分类任务关系失败");
        }
        // 2. 再建立新的分类任务关系
        if (categoryTaskMapper.saveCategoryTask(categoryId, taskId) == 0) {
            return Result.error("建立分类任务关系失败");
        }
        return Result.success();
    }

    private CategoryVO convertToCategoryVO(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setCategoryId(category.getCategoryId());
        categoryVO.setCategoryName(category.getCategoryName());
        return categoryVO;
    }
}
