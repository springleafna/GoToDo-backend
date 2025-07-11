package com.springleaf.gotodo.enums;

import lombok.Getter;

/**
 * 收藏状态枚举 (1:已收藏 0:已取消)
 */
@Getter
public enum FavoriteStatusEnum {

    FAVORITE(1, "已收藏"),
    CANCELLED(0, "已取消");

    private final int code;
    private final String desc;

    FavoriteStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FavoriteStatusEnum fromCode(int code) {
        for (FavoriteStatusEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid favorite status code: " + code);
    }
}
