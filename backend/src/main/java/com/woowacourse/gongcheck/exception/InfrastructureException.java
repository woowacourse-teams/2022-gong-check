package com.woowacourse.gongcheck.exception;

public class InfrastructureException extends CustomException {

    public InfrastructureException(final String message, final ErrorCode errorCode) {
        super(message, errorCode);
    }
}
