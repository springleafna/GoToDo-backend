package com.springleaf.gotodo.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskSortDTO {
    /**
     * 任务分类ID
     */
    private Long categoryId;
    /**
     * 任务ID排序列表
     */
    private List<Long> taskIds;
}
