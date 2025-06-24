package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.GroupCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    void deleteGroupCategoryByCategoryId(Long categoryId);
}
