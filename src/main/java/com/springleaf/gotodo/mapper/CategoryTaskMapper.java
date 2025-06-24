package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.CategoryTask;
import com.springleaf.gotodo.model.entity.Task;
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

    /**
     * 获取任务和分类关联关系
     */
    CategoryTask getCategoryTaskByTaskId(@Param("taskId") Long taskId, @Param("categoryId") Long categoryId);
}
