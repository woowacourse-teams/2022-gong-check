package com.woowacourse.gongcheck.application.response;

import lombok.Getter;

@Getter
public class TokenResponse {

    private String token;
    private boolean existHost;

    private TokenResponse() {
    }

    private TokenResponse(final String token, final boolean existHost) {
        this.token = token;
        this.existHost = existHost;
    }

    public static TokenResponse of(final String token, final boolean existHost) {
        return new TokenResponse(token, existHost);
    }
}
