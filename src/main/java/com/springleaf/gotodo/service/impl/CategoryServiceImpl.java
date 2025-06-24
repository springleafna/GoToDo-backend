package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.DeletedStatusEnum;
import com.springleaf.gotodo.enums.ItemTypeEnum;
import com.springleaf.gotodo.mapper.CategoryMapper;
import com.springleaf.gotodo.mapper.CategoryTaskMapper;
import com.springleaf.gotodo.mapper.DisplayItemMapper;
import com.springleaf.gotodo.mapper.TaskMapper;
import com.springleaf.gotodo.model.entity.Category;
import com.springleaf.gotodo.model.entity.CategoryTask;
import com.springleaf.gotodo.model.entity.DisplayItem;
import com.springleaf.gotodo.model.vo.CategoryVO;
import com.springleaf.gotodo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final TaskMapper taskMapper;
    private final CategoryTaskMapper categoryTaskMapper;
    private final DisplayItemMapper displayItemMapper;

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

    @Transactional
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
        // 保存记录到展示项表中
        DisplayItem displayItem = new DisplayItem();
        displayItem.setItemType(ItemTypeEnum.CATEGORY.getCode());
        displayItem.setItemRefId(category.getCategoryId());
        displayItem.setSortOrder(0);
        if (displayItemMapper.saveDisplayItem(displayItem) == 0) {
            return Result.error("保存展示项失败");
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
        // 1. 检查是否已经存在关联
        if (categoryTaskMapper.getCategoryTaskByTaskId(taskId, categoryId) != null) {
            return Result.success();
        }
        // 2. 先删除原有的分类任务关系
        if (categoryTaskMapper.deleteTaskCategoryByTaskId(taskId) == 0) {
            return Result.error("删除分类任务关系失败");
        }
        // 3. 再建立新的分类任务关系
        if (categoryTaskMapper.saveCategoryTask(categoryId, taskId) == 0) {
            return Result.error("建立分类任务关系失败");
        }
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务管理，确保操作的原子性
    public Result<Void> sortTask(Long categoryId, List<Long> taskIds) {
        if (categoryId == null) {
            return Result.error("分类ID不能为空");
        }
        if (taskIds == null || taskIds.isEmpty()) {
            return Result.error("任务ID列表不能为空");
        }

        // 过滤无效的任务ID（如null或非正数）
        List<Long> validTaskIds = taskIds.stream()
                .filter(Objects::nonNull)
                .filter(id -> id > 0)
                .collect(Collectors.toList());
        if (validTaskIds.size() != taskIds.size()) {
            return Result.error("任务ID列表包含无效的ID");
        }

        // 批量查询所有相关的分类任务关系，避免循环中逐个查询
        List<CategoryTask> existingCategoryTasks = categoryTaskMapper.getCategoryTasksByCategoryIdAndTaskIds(categoryId, validTaskIds);
        Set<Long> existingTaskIdSet = existingCategoryTasks.stream()
                .map(CategoryTask::getTaskId)
                .collect(Collectors.toSet());

        // 找出不存在分类关系的任务ID
        List<Long> missingTaskIds = validTaskIds.stream()
                .filter(taskId -> !existingTaskIdSet.contains(taskId))
                .toList();
        if (!missingTaskIds.isEmpty()) {
            return Result.error("以下任务的分类关系不存在: " + missingTaskIds);
        }

        // 准备批量更新的数据
        List<CategoryTask> updateList = new ArrayList<>();
        for (int i = 0; i < validTaskIds.size(); i++) {
            CategoryTask task = new CategoryTask();
            task.setCategoryId(categoryId);
            task.setTaskId(validTaskIds.get(i));
            task.setSortOrder(i); // 根据传入的顺序设置排序字段
            updateList.add(task);
        }

        // 批量更新排序顺序
        int updatedRows = categoryTaskMapper.batchUpdateCategoryTaskSortOrder(updateList);
        if (updatedRows != updateList.size()) {
            return Result.error("部分任务的排序更新失败");
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
