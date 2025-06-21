package com.springleaf.gotodo.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(0, "操作成功"),
    ERROR(1, "操作失败");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
