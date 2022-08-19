package com.woowacourse.gongcheck.auth.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OAuthAccessTokenRequest {

    private String code;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    private OAuthAccessTokenRequest() {
    }

    public OAuthAccessTokenRequest(final String code, final String clientId, final String clientSecret) {
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
