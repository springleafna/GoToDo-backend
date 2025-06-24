package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.DeletedStatusEnum;
import com.springleaf.gotodo.enums.ItemTypeEnum;
import com.springleaf.gotodo.mapper.*;
import com.springleaf.gotodo.model.entity.DisplayItem;
import com.springleaf.gotodo.model.entity.Group;
import com.springleaf.gotodo.model.entity.GroupCategory;
import com.springleaf.gotodo.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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
        displayItem.setSortOrder(0);
        if (displayItemMapper.saveDisplayItem(displayItem) == 0) {
            return Result.error("保存展示项失败");
        }
        return Result.success();
    }
}
