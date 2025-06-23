package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /**
     * 通过任务分类ID查找任务分类
     */
    Category getCategoryByCategoryId(Long categoryId);
}
