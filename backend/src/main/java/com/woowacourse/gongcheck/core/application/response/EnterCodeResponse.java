package com.woowacourse.gongcheck.core.application.response;

import lombok.Getter;

@Getter
public class EnterCodeResponse {

    private String enterCode;

    private EnterCodeResponse() {
    }

    private EnterCodeResponse(String enterCode) {
        this.enterCode = enterCode;
    }

    public static EnterCodeResponse from(final String enterCode) {
        return new EnterCodeResponse(enterCode);
    }
}
