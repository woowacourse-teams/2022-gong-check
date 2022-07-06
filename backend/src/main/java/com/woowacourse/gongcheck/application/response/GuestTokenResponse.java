package com.woowacourse.gongcheck.application.response;

import lombok.Getter;

@Getter
public class GuestTokenResponse {

    private String token;

    private GuestTokenResponse() {
    }

    private GuestTokenResponse(final String token) {
        this.token = token;
    }

    public static GuestTokenResponse from(final String token) {
        return new GuestTokenResponse(token);
    }
}
