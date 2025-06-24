package com.springleaf.gotodo.model.vo;

import lombok.Data;

@Data
public class DisplayItemVO {

    /**
     * 类型(group:组 category:分类)
     */
    private String itemType;

    /**
     * 关联的 group_id 或 category_id
     */
    private Long itemRefId;

    /**
     * 展示项名称
     */
    private String itemName;

    /**
     * 展示顺序
     */
    private Integer sortOrder;
}
