package com.springleaf.gotodo.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategorySortDTO {

    /**
     * 任务组ID
     */
    private Long groupId;

    /**
     * 任务类ID排序列表
     */
    private List<Long> categoryIds;
}
