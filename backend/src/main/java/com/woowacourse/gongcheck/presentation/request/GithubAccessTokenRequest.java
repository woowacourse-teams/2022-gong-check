package com.woowacourse.gongcheck.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubAccessTokenRequest {

    private String code;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;

    private GithubAccessTokenRequest() {
    }

    public GithubAccessTokenRequest(final String code, final String clientId, final String clientSecret) {
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getCode() {
        return code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
