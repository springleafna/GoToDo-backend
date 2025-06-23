package com.springleaf.gotodo.enums;

import lombok.Getter;

/**
 * 展示项类型枚举
 * 用于区分是任务组(group) 还是 任务分类(category)
 */
@Getter
public enum ItemTypeEnum {

    GROUP("group", "任务组"),
    CATEGORY("category", "任务分类");

    private final String code;
    private final String desc;

    ItemTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据字符串获取对应的枚举
     */
    public static ItemTypeEnum fromCode(String code) {
        for (ItemTypeEnum type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ItemType code: " + code);
    }
}
