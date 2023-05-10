package com.woowacourse.imagestorage.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String message;

    private ErrorResponse() {
    }

    public ErrorResponse(final String message) {
        this.message = message;
    }

    public static ErrorResponse from(final RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    public static ErrorResponse from(final String message) {
        return new ErrorResponse(message);
    }
}
