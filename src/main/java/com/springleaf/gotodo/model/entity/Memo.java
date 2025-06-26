package com.springleaf.gotodo.model.entity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 便签实体类
 */
@Data
public class Memo {
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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记 (0:正常 1:删除)
     */
    private Integer deleted;
}
