package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.vo.CategoryVO;

import java.util.List;

public interface GroupService {
    /**
     * 将一个任务类移动到某个任务组下
     */
    Result<Void> moveCategoryToGroup(Long categoryId, Long groupId);

    /**
     * 保存任务组
     */
    Result<Void> saveGroup(String groupName);

    /**
     * 任务类排序
     * @param groupId 任务组id
     * @param categoryIds 任务类id列表
     * @return Result<Void>
     */
    Result<Void> sortCategory(Long groupId, List<Long> categoryIds);

    /**
     * 获取任务组下的所有任务类
     */
    Result<List<CategoryVO>> listCategoryByGroup(Long groupId);
}
