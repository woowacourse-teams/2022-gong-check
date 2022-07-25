package com.woowacourse.gongcheck.auth.application.response;

import lombok.Getter;

@Getter
public class TokenResponse {

    private String token;
    private boolean alreadyJoin;

    private TokenResponse() {
    }

    private TokenResponse(final String token, final boolean alreadyJoin) {
        this.token = token;
        this.alreadyJoin = alreadyJoin;
    }

    public static TokenResponse of(final String token, final boolean alreadyJoin) {
        return new TokenResponse(token, alreadyJoin);
    }
}
