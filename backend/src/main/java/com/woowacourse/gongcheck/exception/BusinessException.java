package com.woowacourse.gongcheck.exception;

public class BusinessException extends CustomException {

    public BusinessException(final String message, final ErrorCode errorCode) {
        super(message, errorCode);
    }
}
