package com.woowacourse.gongcheck.exception;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(final String message, final ErrorCode errorCode) {
        super(message, errorCode);
    }
}
