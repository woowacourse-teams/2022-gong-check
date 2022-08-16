package com.woowacourse.gongcheck.infrastructure.oauth;

import com.woowacourse.gongcheck.auth.application.OAuthClient;
import com.woowacourse.gongcheck.auth.application.response.OAuthAccessTokenResponse;
import com.woowacourse.gongcheck.auth.application.response.SocialProfileResponse;
import com.woowacourse.gongcheck.auth.presentation.request.OAuthAccessTokenRequest;
import com.woowacourse.gongcheck.exception.ErrorCode;
import com.woowacourse.gongcheck.exception.InfrastructureException;
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
public class GithubOauthClient implements OAuthClient {

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

    @Override
    public SocialProfileResponse requestSocialProfileByCode(final String code) {
        return requestGithubProfile(requestAccessToken(code));
    }

    private String requestAccessToken(final String code) {
        OAuthAccessTokenRequest oAuthAccessTokenRequest = new OAuthAccessTokenRequest(code, clientId, clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(oAuthAccessTokenRequest, headers);

        OAuthAccessTokenResponse oAuthAccessTokenResponse = exchangeRestTemplateBody(tokenUrl, HttpMethod.POST,
                httpEntity, OAuthAccessTokenResponse.class);
        if (Objects.isNull(oAuthAccessTokenResponse)) {
            log.error("github oauth error. clientId = {}, clientSecret = {}, tokenUrl = {}, profileUrl = {}", clientId,
                    clientSecret, tokenUrl, profileUrl);
            throw new InfrastructureException("잘못된 요청입니다.", ErrorCode.I006);
        }
        return oAuthAccessTokenResponse.getAccessToken();
    }

    private SocialProfileResponse requestGithubProfile(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, BEARER_TYPE + accessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        return exchangeRestTemplateBody(profileUrl, HttpMethod.GET, httpEntity, SocialProfileResponse.class);
    }

    private <T> T exchangeRestTemplateBody(final String url, final HttpMethod httpMethod,
                                           final HttpEntity<?> httpEntity, final Class<T> exchangeType) {
        try {
            return restTemplate
                    .exchange(url, httpMethod, httpEntity, exchangeType)
                    .getBody();
        } catch (HttpClientErrorException | NullPointerException e) {
            log.error(
                    "github oauth error. clientId = {}, clientSecret = {}, tokenUrl = {}, profileUrl = {}, message = {}",
                    clientId, clientSecret, tokenUrl, profileUrl, e.getMessage());
            throw new InfrastructureException("해당 사용자의 프로필을 요청할 수 없습니다.", ErrorCode.I007);
        }
    }
}
