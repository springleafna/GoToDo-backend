package com.springleaf.gotodo.model.dto;

import lombok.Data;

@Data
public class MemoSaveDTO {

    /**
     * 便签标题
     */
    private String title;

    /**
     * 便签内容
     */
    private String content;
}
