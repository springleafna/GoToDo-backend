package com.springleaf.gotodo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryTaskMapper {
    int saveCategoryTask(@Param("categoryId") Long categoryId, @Param("taskId") Long taskId);
}
