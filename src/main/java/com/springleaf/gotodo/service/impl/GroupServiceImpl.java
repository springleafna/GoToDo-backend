package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.DeletedStatusEnum;
import com.springleaf.gotodo.enums.ItemTypeEnum;
import com.springleaf.gotodo.mapper.*;
import com.springleaf.gotodo.model.entity.Category;
import com.springleaf.gotodo.model.entity.DisplayItem;
import com.springleaf.gotodo.model.entity.Group;
import com.springleaf.gotodo.model.entity.GroupCategory;
import com.springleaf.gotodo.model.vo.CategoryVO;
import com.springleaf.gotodo.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;
    private final CategoryMapper categoryMapper;
    private final GroupCategoryMapper groupCategoryMapper;
    private final DisplayItemMapper displayItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Void> moveCategoryToGroup(Long categoryId, Long groupId) {
        if (categoryId == null) {
            return Result.error("任务分类ID不能为空");
        }
        if (groupId == null) {
            return Result.error("任务组ID不能为空");
        }
        if (categoryMapper.getCategoryByCategoryId(categoryId) == null) {
            return Result.error("任务分类不存在");
        }
        if (groupMapper.getGroupByGroupId(groupId) == null) {
            return Result.error("任务分组不存在");
        }
        // 迁移
        // 需要判断展示项中是否存在该类，也就是说当前该类是否和组同级
        DisplayItem displayItem = displayItemMapper.getDisplayItemByCategoryIdAndType(categoryId, ItemTypeEnum.CATEGORY.getCode());
        if (displayItem != null) {
            // 与任务组同级时需要先删除该展示项
            if (displayItemMapper.deleteDisplayItemById(displayItem.getItemId()) == 0) {
                return Result.error("删除展示项失败");
            }
        }
        // 判断该分类是否属于其他任务组，若属于则移除
        GroupCategory groupCategory = groupCategoryMapper.getGroupCategoryByCategoryId(categoryId);
        if (groupCategory != null) {
            if (groupCategory.getCategoryId().equals(categoryId) && groupCategory.getGroupId().equals(groupId)) {
                return Result.success();
            }
            groupCategoryMapper.deleteGroupCategoryByCategoryId(categoryId);
        }
        // 再添加至当前任务组
        int sortOrder = groupCategoryMapper.getMaxSortOrderByGroupId(groupId);
        groupCategoryMapper.saveGroupCategory(groupId, categoryId, sortOrder + 1);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Void> saveGroup(String groupName) {
        if (groupName == null || groupName.isBlank()) {
            return Result.error("任务组名称不能为空");
        }

        Group group = new Group();
        group.setGroupName(groupName);
        group.setDeleted(DeletedStatusEnum.NORMAL.getCode());
        if (groupMapper.saveGroup(group) == 0) {
            return Result.error("任务组保存失败");
        }
        // 保存记录到展示项表中
        DisplayItem displayItem = new DisplayItem();
        displayItem.setItemType(ItemTypeEnum.GROUP.getCode());
        displayItem.setItemRefId(group.getGroupId());
        displayItem.setSortOrder(displayItemMapper.getMaxSortOrder() + 1);
        if (displayItemMapper.saveDisplayItem(displayItem) == 0) {
            return Result.error("保存展示项失败");
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Void> sortCategory(Long groupId, List<Long> categoryIds) {
        log.info("开始排序任务类, groupId: {}, categoryIds: {}", groupId, categoryIds);
        if (groupId == null) {
            return Result.error("任务组ID不能为空");
        }
        if (categoryIds == null || categoryIds.isEmpty()) {
            return Result.error("任务分类ID列表不能为空");
        }
        if (groupMapper.getGroupByGroupId(groupId) == null) {
            return Result.error("任务组不存在");
        }

        // 过滤无效的分类ID（如null或非正数）
        List<Long> validCategoryIds = categoryIds.stream()
                .filter(Objects::nonNull)
                .filter(id -> id > 0)
                .distinct()
                .toList();
        if (validCategoryIds.size() != categoryIds.size()) {
            log.error("任务类ID列表包含无效或重复的ID, 原始数量: {}, 有效数量: {}",
                    categoryIds.size(), validCategoryIds.size());
            return Result.error("任务类ID列表包含无效的ID");
        }

        // 批量查询所有相关的组-分类关系，避免循环中逐个查询
        List<GroupCategory> existingCategoryTasks = groupCategoryMapper.getGroupCategoriesByGroupIdAndCategoryIds(groupId, validCategoryIds);
        Set<Long> existingTaskIdSet = existingCategoryTasks.stream()
                .map(GroupCategory::getCategoryId)
                .collect(Collectors.toSet());

        // 找出不存在分类关系的任务ID
        List<Long> missingTaskIds = validCategoryIds.stream()
                .filter(categoryId -> !existingTaskIdSet.contains(categoryId))
                .toList();
        if (!missingTaskIds.isEmpty()) {
            return Result.error("以下任务的分类关系不存在: " + missingTaskIds);
        }

        // 准备批量更新的数据
        List<GroupCategory> updateList = new ArrayList<>();
        for (int i = 0; i < validCategoryIds.size(); i++) {
            GroupCategory groupCategory = new GroupCategory();
            groupCategory.setGroupId(groupId);
            groupCategory.setCategoryId(validCategoryIds.get(i));
            groupCategory.setSortOrder(i); // 根据传入的顺序设置排序字段
            updateList.add(groupCategory);
        }

        // 批量更新排序顺序
        int updatedRows = groupCategoryMapper.batchUpdateGroupCategorySortOrder(updateList);
        if (updatedRows != updateList.size()) {
            return Result.error("部分任务类的排序更新失败");
        }

        log.info("任务排序成功, 共更新{}条记录", updateList.size());
        return Result.success();
    }

    @Override
    public Result<List<CategoryVO>> listCategoryByGroup(Long groupId) {
        if (groupId == null) {
            return Result.error("任务组ID不能为空");
        }
        if (groupMapper.getGroupByGroupId(groupId) == null) {
            return Result.error("任务组不存在");
        }
        List<GroupCategory> groupCategories = groupCategoryMapper.getGroupCategoriesByGroupId(groupId);
        if (groupCategories == null || groupCategories.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        // 提取所有的分类ID
        Set<Long> categoryIds = groupCategories.stream()
                .map(GroupCategory::getCategoryId)
                .collect(Collectors.toSet());
        List<Category> categoryList = categoryMapper.getCategoriesByIds(categoryIds);
        // 将分类信息转换为Map，便于快速查找
        Map<Long, Category> categoryMap = categoryList.stream()
                .collect(Collectors.toMap(Category::getCategoryId, Function.identity(), (existing, replacement) -> existing));
        // 构建返回的CategoryVO列表
        List<CategoryVO> categoryVOList = groupCategories.stream()
                .map(groupCategory -> {
                    Category category = categoryMap.get(groupCategory.getCategoryId());
                    if (category == null) {
                        // 如果分类信息缺失，可以选择跳过或设置默认值
                        return null;
                    }
                    CategoryVO categoryVO = new CategoryVO();
                    categoryVO.setCategoryId(category.getCategoryId());
                    categoryVO.setCategoryName(category.getCategoryName());
                    categoryVO.setSortOrder(groupCategory.getSortOrder());
                    return categoryVO;
                })
                .filter(Objects::nonNull) // 过滤掉可能的null值
                .collect(Collectors.toList());
        return Result.success(categoryVOList);
    }
}
