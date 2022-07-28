package com.woowacourse.gongcheck.auth.application;

import static com.woowacourse.gongcheck.auth.domain.Authority.HOST;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.woowacourse.gongcheck.auth.application.response.GithubProfileResponse;
import com.woowacourse.gongcheck.auth.application.response.TokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.TokenRequest;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
import com.woowacourse.gongcheck.infrastructure.oauth.GithubOauthClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("HostAuthService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HostAuthServiceTest {

    @Autowired
    private HostAuthService hostAuthService;

    @MockBean
    private GithubOauthClient githubOauthClient;

    @Autowired
    private HostRepository hostRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Nested
    class createToken_메소드는 {

        private static final String GITHUB_NICKNAME = "jinyoungchoi95";
        private static final String GITHUB_LOGIN_NAME = "jinyoungchoi95";
        private static final String GITHUB_ID = "1234567";
        private static final String GITHUB_IMAGE_URL = "https://github.com";

        @Nested
        class 새로_가입한_Host의_oauth_토큰_코드가_입력될_경우 {

            private static final String OAUTH_CODE = "1234";
            private static final String JWT_ACCESS_TOKEN = "jwt.token.here";
            private TokenRequest tokenRequest;

            @BeforeEach
            void setUp() {
                when(githubOauthClient.requestGithubProfileByCode(OAUTH_CODE))
                        .thenReturn(new GithubProfileResponse(GITHUB_NICKNAME, GITHUB_LOGIN_NAME, GITHUB_ID,
                                GITHUB_IMAGE_URL));
                when(jwtTokenProvider.createToken(any(), eq(HOST))).thenReturn(JWT_ACCESS_TOKEN);
                tokenRequest = new TokenRequest(OAUTH_CODE);
            }

            @Test
            void 유저를_가입시키고_토큰과_기존유저가_아님을_반환한다() {
                TokenResponse actual = hostAuthService.createToken(tokenRequest);

                assertThat(actual)
                        .usingRecursiveComparison()
                        .isEqualTo(new TokenResponse(JWT_ACCESS_TOKEN, false));
            }
        }

        @Nested
        class 기존에_가입한_Host의_oauth_토큰_코드가_입력될_경우 {

            private static final String OAUTH_CODE = "1234";
            private static final String JWT_ACCESS_TOKEN = "jwt.token.here";
            private TokenRequest tokenRequest;

            @BeforeEach
            void setUp() {
                when(githubOauthClient.requestGithubProfileByCode(OAUTH_CODE))
                        .thenReturn(new GithubProfileResponse(GITHUB_NICKNAME, GITHUB_LOGIN_NAME, GITHUB_ID,
                                GITHUB_IMAGE_URL));
                when(jwtTokenProvider.createToken(any(), eq(HOST))).thenReturn(JWT_ACCESS_TOKEN);
                hostRepository.save(Host_생성("1234", Long.parseLong(GITHUB_ID)));
                tokenRequest = new TokenRequest(OAUTH_CODE);
            }

            @Test
            void 기존의_유저에_대한_토큰과_기존유저임을_반환한다() {
                TokenResponse actual = hostAuthService.createToken(tokenRequest);

                assertThat(actual)
                        .usingRecursiveComparison()
                        .isEqualTo(new TokenResponse(JWT_ACCESS_TOKEN, true));
            }
        }

        @Nested
        class 잘못된_oauth_토큰_코드가_입력될_경우 {

            private static final String ERROR_OAUTH_CODE = "1234";
            private TokenRequest tokenRequest;

            @BeforeEach
            void setUp() {
                when(githubOauthClient.requestGithubProfileByCode(ERROR_OAUTH_CODE))
                        .thenThrow(UnauthorizedException.class);
                tokenRequest = new TokenRequest(ERROR_OAUTH_CODE);
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> hostAuthService.createToken(tokenRequest))
                        .isInstanceOf(UnauthorizedException.class);
            }
        }
    }
}
