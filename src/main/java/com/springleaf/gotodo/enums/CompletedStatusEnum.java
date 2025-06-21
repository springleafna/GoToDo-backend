package com.springleaf.gotodo.enums;

/**
 * 完成状态枚举 (0:未完成 1:已完成)
 */
public enum CompletedStatusEnum {

    NOT_COMPLETED(0, "未完成"),
    COMPLETED(1, "已完成");

    private final int code;
    private final String desc;

    CompletedStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CompletedStatusEnum fromCode(int code) {
        for (CompletedStatusEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid completed status code: " + code);
    }
}
