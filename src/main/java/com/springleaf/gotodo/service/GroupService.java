package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;

public interface GroupService {
    /**
     * 将一个任务类移动到某个任务组下
     */
    Result<Void> moveCategoryToGroup(Long categoryId, Long groupId);

    /**
     * 保存任务组
     */
    Result<Void> saveGroup(String groupName);
}
