package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.GithubAccessTokenResponse;
import com.woowacourse.gongcheck.application.response.GithubProfileResponse;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
import com.woowacourse.gongcheck.presentation.request.GithubAccessTokenRequest;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubOauthClient {

    private static final String BEARER_TYPE = "Bearer ";

    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl;
    private final String profileUrl;
    private final RestTemplate restTemplate;

    public GithubOauthClient(@Value("${github.client.id}") final String clientId,
                             @Value("${github.client.secret}") final String clientSecret,
                             @Value("${github.url.token}") final String tokenUrl,
                             @Value("${github.url.profile}") final String profileUrl,
                             final RestTemplate restTemplate) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUrl = tokenUrl;
        this.profileUrl = profileUrl;
        this.restTemplate = restTemplate;
    }

    public String requestAccessToken(String code) {
        GithubAccessTokenRequest githubAccessTokenRequest = new GithubAccessTokenRequest(code, clientId, clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(githubAccessTokenRequest, headers);

        String accessToken = restTemplate
                .exchange(tokenUrl, HttpMethod.POST, httpEntity, GithubAccessTokenResponse.class)
                .getBody()
                .getAccessToken();
        if (Objects.isNull(accessToken)) {
            throw new UnauthorizedException("잘못된 요청입니다.");
        }
        return accessToken;
    }

    public GithubProfileResponse requestGithubProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, BEARER_TYPE + accessToken);

        HttpEntity httpEntity = new HttpEntity<>(headers);

        return restTemplate
                .exchange(profileUrl, HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                .getBody();
    }
}
