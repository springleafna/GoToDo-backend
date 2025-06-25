package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface CategoryMapper {
    /**
     * 通过任务分类ID查找任务分类
     */
    Category getCategoryByCategoryId(Long categoryId);

    /**
     * 获取所有分类列表
     */
    List<Category> listCategory();

    /**
     * 保存分类
     */
    int saveCategory(Category category);

    /**
     * 通过分类ID列表查找分类
     */
    List<Category> getCategoriesByIds(Set<Long> categoryIds);
}
