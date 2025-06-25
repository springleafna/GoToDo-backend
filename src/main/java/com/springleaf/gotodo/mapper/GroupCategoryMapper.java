package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.GroupCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupCategoryMapper {
    /**
     * 添加任务分类到任务组
     */
    int saveGroupCategory(@Param("groupId") Long groupId, @Param("categoryId") Long categoryId, @Param("sortOrder") int sortOrder);

    /**
     * 删除任务分类和任务组的关联关系
     */
    int deleteGroupCategory(@Param("groupId") Long groupId, @Param("categoryId") Long categoryId);

    /**
     * 根据任务组ID和分类ID查找关联关系
     */
    GroupCategory getGroupCategoryByGroupIdAndCategoryId(@Param("groupId") Long groupId, @Param("categoryId") Long categoryId);

    /**
     * 根据分类ID查找关联关系
     */
    GroupCategory getGroupCategoryByCategoryId(Long categoryId);

    /**
     * 根据分类ID删除关联关系
     */
    int deleteGroupCategoryByCategoryId(Long categoryId);

    /**
     * 根据任务组ID和分类ID列表查找关联关系
     */
    List<GroupCategory> getGroupCategoriesByGroupIdAndCategoryIds(@Param("groupId") Long groupId, @Param("validCategoryIds") List<Long> validCategoryIds);

    /**
     * 批量更新任务组-任务分类排序
     */
    int batchUpdateGroupCategorySortOrder(List<GroupCategory> updateList);

    /**
     * 获取任务组中最大的排序值
     */
    int getMaxSortOrderByGroupId(Long groupId);

    /**
     * 根据任务组ID获取所有任务分类
     */
    List<GroupCategory> getGroupCategoriesByGroupId(Long groupId);
}
