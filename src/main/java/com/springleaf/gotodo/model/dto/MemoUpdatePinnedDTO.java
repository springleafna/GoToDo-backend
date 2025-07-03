package com.springleaf.gotodo.model.dto;

import lombok.Data;

@Data
public class MemoUpdatePinnedDTO {
    /**
     * 便签ID
     */
    private Long memoId;

    /**
     * 是否置顶 (false:否 true:是)
     */
    private Boolean pinned;
}
