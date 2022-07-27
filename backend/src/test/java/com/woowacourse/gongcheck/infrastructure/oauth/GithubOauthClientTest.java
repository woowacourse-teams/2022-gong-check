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
import org.junit.jupiter.api.Nested;
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

    @Nested
    class requestGithubProfileByCode_메소드는 {

        @Nested
        class 깃허브에서_access_token_반환이_불가능한_경우 {

            @BeforeEach
            void setUp() {
                mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                        .andExpect(method(HttpMethod.POST))
                        .andRespond(withStatus(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> githubOauthClient.requestGithubProfileByCode("code"))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("해당 사용자의 프로필을 요청할 수 없습니다.");
                mockRestServiceServer.verify();
            }
        }

        @Nested
        class 권한이_없는_code을_입력하여_깃허브_access_Token를_요청할_경우 {

            @BeforeEach
            void setUp() throws JsonProcessingException {
                mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                        .andExpect(method(HttpMethod.POST))
                        .andRespond(withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objectMapper.writeValueAsString(null)));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> githubOauthClient.requestGithubProfileByCode("code"))
                        .isInstanceOf(UnauthorizedException.class)
                        .hasMessage("잘못된 요청입니다.");
                mockRestServiceServer.verify();
            }
        }

        @Nested
        class 깃허브에서_프로필_반환이_불가능한_경우 {

            @BeforeEach
            void setUp() throws JsonProcessingException {
                GithubAccessTokenResponse token = new GithubAccessTokenResponse("access_token");
                mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                        .andExpect(method(HttpMethod.POST))
                        .andRespond(withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objectMapper.writeValueAsString(token)));

                mockRestServiceServer.expect(requestTo("https://api.github.com/user"))
                        .andExpect(method(HttpMethod.GET))
                        .andRespond(withStatus(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> githubOauthClient.requestGithubProfileByCode("code"))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("해당 사용자의 프로필을 요청할 수 없습니다.");
                mockRestServiceServer.verify();
            }
        }

        @Nested
        class 권한이_있는_code로_정상적으로_깃허브에_프로필접근이_가능한_경우 {

            private GithubProfileResponse githubProfileResponse;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                GithubAccessTokenResponse token = new GithubAccessTokenResponse("access_token");
                mockRestServiceServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                        .andExpect(method(HttpMethod.POST))
                        .andRespond(withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objectMapper.writeValueAsString(token)));

                githubProfileResponse = new GithubProfileResponse("nickname", "loginName", "1", "test.com");
                mockRestServiceServer.expect(requestTo("https://api.github.com/user"))
                        .andExpect(method(HttpMethod.GET))
                        .andRespond(withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objectMapper.writeValueAsString(githubProfileResponse)));
            }

            @Test
            void 깃허브_프로필정보를_반환한다() {
                GithubProfileResponse actual = githubOauthClient.requestGithubProfileByCode("access_token");

                assertThat(actual)
                        .usingRecursiveComparison()
                        .isEqualTo(githubProfileResponse);
                mockRestServiceServer.verify();
            }
        }
    }
}
