package com.woowacourse.gongcheck.infrastructure.oauth;

import com.woowacourse.gongcheck.auth.application.response.GithubAccessTokenResponse;
import com.woowacourse.gongcheck.auth.application.response.GithubProfileResponse;
import com.woowacourse.gongcheck.auth.presentation.request.GithubAccessTokenRequest;
import com.woowacourse.gongcheck.exception.InfrastructureException;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
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

    public GithubProfileResponse requestGithubProfileByCode(final String code) {
        return requestGithubProfile(requestAccessToken(code));
    }

    private String requestAccessToken(final String code) {
        GithubAccessTokenRequest githubAccessTokenRequest = new GithubAccessTokenRequest(code, clientId, clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(githubAccessTokenRequest, headers);

        GithubAccessTokenResponse githubAccessTokenResponse = exchangeRestTemplateBody(tokenUrl, HttpMethod.POST,
                httpEntity, GithubAccessTokenResponse.class);
        if (Objects.isNull(githubAccessTokenResponse)) {
            throw new UnauthorizedException("잘못된 요청입니다.");
        }
        return githubAccessTokenResponse.getAccessToken();
    }

    private GithubProfileResponse requestGithubProfile(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, BEARER_TYPE + accessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        return exchangeRestTemplateBody(profileUrl, HttpMethod.GET, httpEntity, GithubProfileResponse.class);
    }

    private <T> T exchangeRestTemplateBody(final String url, final HttpMethod httpMethod,
                                           final HttpEntity<?> httpEntity, final Class<T> exchangeType) {
        try {
            return restTemplate
                    .exchange(url, httpMethod, httpEntity, exchangeType)
                    .getBody();
        } catch (HttpClientErrorException | NullPointerException e) {
            log.error(e.getMessage());
            throw new InfrastructureException("해당 사용자의 프로필을 요청할 수 없습니다.");
        }
    }
}
