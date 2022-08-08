package com.woowacourse.gongcheck.exception;

import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public class ErrorResponse {

    private String errorCode;

    private ErrorResponse() {
    }

    private ErrorResponse(final String errorCode) {
        this.errorCode = errorCode;
    }

    public static ErrorResponse from(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name());
    }

    public static ErrorResponse from(final MethodArgumentNotValidException e) {
        return new ErrorResponse(e.getAllErrors()
                .get(0)
                .getDefaultMessage());
    }
}
