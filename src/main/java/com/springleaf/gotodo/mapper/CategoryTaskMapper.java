package com.springleaf.gotodo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryTaskMapper {
    /**
     * 保存任务和分类关联关系
     */
    int saveCategoryTask(@Param("categoryId") Long categoryId, @Param("taskId") Long taskId);

    /**
     * 删除任务和分类关联关系
     */
    int deleteTaskCategoryByTaskId(Long taskId);
}
