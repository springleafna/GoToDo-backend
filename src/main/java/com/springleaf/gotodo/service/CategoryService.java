package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有分类列表
     * @return List<CategoryVO> 分类列表
     */
    Result<List<CategoryVO>> listCategory();

    /**
     * 保存分类
     * @param categoryName 分类名称
     * @return Result<Void>
     */
    Result<Void> saveCategory(String categoryName);
}
