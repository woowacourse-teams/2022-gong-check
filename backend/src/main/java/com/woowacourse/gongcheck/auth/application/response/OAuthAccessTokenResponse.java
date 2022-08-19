package com.woowacourse.gongcheck.auth.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OAuthAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private OAuthAccessTokenResponse() {
    }

    public OAuthAccessTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
