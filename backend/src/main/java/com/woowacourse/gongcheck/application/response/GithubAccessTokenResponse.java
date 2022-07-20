package com.woowacourse.gongcheck.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private GithubAccessTokenResponse() {
    }

    public GithubAccessTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
