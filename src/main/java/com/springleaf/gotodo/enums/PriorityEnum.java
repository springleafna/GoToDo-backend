package com.springleaf.gotodo.enums;

import lombok.Getter;

/**
 * 任务优先级枚举 (0:低 1:中 2:高)
 */
@Getter
public enum PriorityEnum {

    LOW(0, "低"),
    MEDIUM(1, "中"),
    HIGH(2, "高");

    private final int code;
    private final String desc;

    PriorityEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PriorityEnum fromCode(int code) {
        for (PriorityEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid priority code: " + code);
    }
}
