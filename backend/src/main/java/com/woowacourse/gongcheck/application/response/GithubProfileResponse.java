package com.woowacourse.gongcheck.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongcheck.domain.host.Host;
import lombok.Getter;

@Getter
public class GithubProfileResponse {

    @JsonProperty("name")
    private String nickname;
    @JsonProperty("login")
    private String loginName;
    @JsonProperty("id")
    private String githubId;
    @JsonProperty("avatar_url")
    private String imageUrl;

    private GithubProfileResponse() {
    }

    public GithubProfileResponse(final String nickname, final String loginName, final String githubId,
                                 final String imageUrl) {
        this.nickname = nickname;
        this.loginName = loginName;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
    }

    public Host toHost() {
        return Host.builder()
                .githubId(getGithubId())
                .imageUrl(imageUrl)
                .build();
    }

    public Long getGithubId() {
        return Long.parseLong(githubId);
    }
}
