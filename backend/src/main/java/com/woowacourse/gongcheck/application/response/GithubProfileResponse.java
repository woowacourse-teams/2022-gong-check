package com.woowacourse.gongcheck.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongcheck.domain.host.Host;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GithubProfileResponse {

    public static final String DEFAULT_SPACE_PASSWORD = "0000";
    
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
                .spacePassword(DEFAULT_SPACE_PASSWORD)
                .githubId(getGithubId())
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Long getGithubId() {
        return Long.parseLong(githubId);
    }
}
