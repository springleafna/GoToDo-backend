package com.springleaf.gotodo.model.entity;

import lombok.Data;

/**
 * 展示项排序表实体类（用于组和平级任务分类的统一排序）
 */
@Data
public class DisplayItem {
    /**
     * 展示项ID
     */
    private Long item_id;

    /**
     * 类型(group:组 category:分类)
     */
    private String itemType;

    /**
     * 关联的 group_id 或 category_id
     */
    private Long itemRefId;

    /**
     * 展示顺序
     */
    private Integer sortOrder;
}
