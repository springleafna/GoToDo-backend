package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.CategoryTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryTaskMapper {
    /**
     * 保存任务和分类关联关系
     */
    int saveCategoryTask(CategoryTask categoryTask);

    /**
     * 删除任务和分类关联关系
     */
    int deleteTaskCategoryByTaskId(Long taskId);

    /**
     * 获取任务和分类关联关系
     */
    CategoryTask getCategoryTaskByTaskId(@Param("taskId") Long taskId, @Param("categoryId") Long categoryId);

    /**
     * 更新任务分类排序
     */
    int updateCategoryTaskSortOrder(@Param("categoryId") Long categoryId, @Param("taskId") Long taskId, @Param("sortOrder") int sortOrder);

    /**
     * 批量查询任务分类关系
     */
    List<CategoryTask> getCategoryTasksByCategoryIdAndTaskIds(@Param("categoryId") Long categoryId, @Param("validTaskIds") List<Long> validTaskIds);

    /**
     * 批量更新任务分类排序
     */
    int batchUpdateCategoryTaskSortOrder(List<CategoryTask> updateCategoryTaskList);

    /**
     * 获取分类的最大排序值
     */
    int getMaxSortOrderByCategoryId(Long categoryId);

    /**
     * 获取分类下的所有任务分类关系
     */
    List<CategoryTask> getCategoryTaskListByCategoryId(Long categoryId);

    /**
     * 获取任务在分类中的排序值
     */
    Integer getSortOrderByTaskId(Long taskId);
}
