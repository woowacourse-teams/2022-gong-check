package com.woowacourse.gongcheck.documentation.support;

import java.util.Map;

public class ErrorCodes {

    Map<String, String> errorCode;

    public ErrorCodes(final Map<String, String> errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, String> getErrorCode() {
        return errorCode;
    }
}
