package com.springleaf.gotodo.model.dto;

import lombok.Data;

@Data
public class MemoUpdateDTO {
    /**
     * 便签ID
     */
    private Long memoId;

    /**
     * 便签标题
     */
    private String title;

    /**
     * 便签内容
     */
    private String content;

    /**
     * 是否置顶 (0:否 1:是)
     */
    private Integer pinned;
}
