package com.springleaf.gotodo.model.dto;


import lombok.Getter;

/**
 * 任务新增DTO
 */
@Getter
public class TaskSaveDTO {

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务分类ID
     */
    private Long categoryId;

}
