package com.springleaf.gotodo.model.vo;

import lombok.Data;

/**
 * 分类视图对象
 */
@Data
public class CategoryVO {
    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 排序
     */
    private Integer sortOrder;
}