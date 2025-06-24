package com.springleaf.gotodo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("全局异常捕获：", e); // 打印详细堆栈

        String msg = e.getMessage();
        if (msg == null || msg.isEmpty()) {
            msg = "服务器内部错误";
        }

        return Result.error(msg);
    }
}