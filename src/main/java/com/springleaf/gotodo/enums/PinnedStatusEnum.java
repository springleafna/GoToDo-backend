package com.springleaf.gotodo.enums;

import lombok.Getter;

@Getter
public enum PinnedStatusEnum {
    
    PINNED(1, "已置顶"),
    UN_PINNED(0, "未置顶");
    
    private final int code;
    private final String desc;
    
    PinnedStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
