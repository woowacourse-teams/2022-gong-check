package com.woowacourse.gongcheck.infrastructure.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongcheck.auth.application.response.GithubAccessTokenResponse;
import com.woowacourse.gongcheck.auth.application.response.GithubProfileResponse;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Transactional
class GithubOauthClientTest {

    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GithubOauthClient githubOauthClient;

    @BeforeEach
    void setUp() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void code를_받아_access_token을_반환한다() throws JsonProcessingException {
        GithubAccessTokenResponse token = new GithubAccessTokenResponse("access_token");
        mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(token)));

        String result = githubOauthClient.requestAccessToken("code");
        mockRestServiceServer.verify();

        assertThat(token.getAccessToken()).isEqualTo(result);
    }

    @Test
    void 깃허브_access_Token_요청에_실패하면_예외가_발생한다() {
        mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));

        assertThatThrownBy(() -> githubOauthClient.requestAccessToken("code"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 사용자의 프로필을 요청할 수 없습니다.");
        mockRestServiceServer.verify();
    }

    @Test
    void 올바르지_않은_code이면_예외가_발생한다() throws JsonProcessingException {
        mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(null)));

        assertThatThrownBy(() -> githubOauthClient.requestAccessToken("code"))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("잘못된 요청입니다.");
        mockRestServiceServer.verify();
    }

    @Test
    void 깃허브_프로필을_요청한다() throws JsonProcessingException {
        GithubProfileResponse githubProfileResponse = new GithubProfileResponse("nickname", "loginName", "1",
                "test.com");
        mockRestServiceServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(githubProfileResponse)));

        GithubProfileResponse result = githubOauthClient.requestGithubProfile("access_token");
        mockRestServiceServer.verify();

        assertThat(githubProfileResponse).usingRecursiveComparison()
                .isEqualTo(result);
    }

    @Test
    void 깃허브_프로필_요청에_실패하면_예외가_발생한다() throws JsonProcessingException {
        mockRestServiceServer.expect(requestTo("https://api.github.com/user"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));

        assertThatThrownBy(() -> githubOauthClient
                .requestGithubProfile("access_token"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 사용자의 프로필을 요청할 수 없습니다.");
        mockRestServiceServer.verify();
    }
}
