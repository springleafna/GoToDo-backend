package com.springleaf.gotodo.enums;

/**
 * 删除标记枚举 (0:正常 1:删除)
 */
public enum DeletedStatusEnum {

    NORMAL(0, "正常"),
    DELETED(1, "已删除");

    private final int code;
    private final String desc;

    DeletedStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DeletedStatusEnum fromCode(int code) {
        for (DeletedStatusEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid deleted status code: " + code);
    }
}
