package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.mapper.CategoryMapper;
import com.springleaf.gotodo.mapper.GroupCategoryMapper;
import com.springleaf.gotodo.mapper.GroupMapper;
import com.springleaf.gotodo.mapper.TaskMapper;
import com.springleaf.gotodo.model.entity.GroupCategory;
import com.springleaf.gotodo.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    
    private final GroupMapper groupMapper;
    private final TaskMapper taskMapper;
    private final CategoryMapper categoryMapper;
    private final GroupCategoryMapper groupCategoryMapper;

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
        // 1. 先判断该分类是否属于其他任务组，若属于则移除
        GroupCategory groupCategory = groupCategoryMapper.getGroupCategoryByCategoryId(categoryId);
        if (groupCategory != null) {
            if (groupCategory.getCategoryId().equals(categoryId) && groupCategory.getGroupId().equals(groupId)) {
                return Result.success();
            }
            groupCategoryMapper.deleteGroupCategoryByCategoryId(categoryId);
        }
        // 2. 再添加至当前任务组
        groupCategoryMapper.saveGroupCategory(groupId, categoryId, 0);
        return Result.success();
    }

    @Override
    public Result<Void> saveGroup(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            return Result.error("任务组名称不能为空");
        }
        if (groupMapper.saveGroup(groupName) == 0) {
            return Result.error("任务组保存失败");
        }
        return Result.success();
    }
}
