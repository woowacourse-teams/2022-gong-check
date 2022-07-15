package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.woowacourse.gongcheck.application.response.GithubProfileResponse;
import com.woowacourse.gongcheck.application.response.TokenResponse;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.presentation.request.TokenRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class HostAuthServiceTest {

    @Autowired
    private HostAuthService hostAuthService;

    @MockBean
    private GithubOauthClient githubOauthClient;

    @Autowired
    private HostRepository hostRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void code를_받아_token과_존재하지_않는_Host이면_false를_반환한다() {
        GithubProfileResponse response = new GithubProfileResponse("nickname", "loginName", "1234",
                "test.com");
        when(githubOauthClient.requestGithubProfile(any())).thenReturn(response);
        when(jwtTokenProvider.createToken(any(), any())).thenReturn("jwt.token.here");

        TokenResponse result = hostAuthService.createToken(new TokenRequest("code"));

        assertThat(result)
                .extracting("token", "existHost")
                .containsExactly("jwt.token.here", false);
    }

    @Test
    void code를_받아_token과_이미_존재하는_Host이면_true를_반환한다() {
        hostRepository.save(Host_생성("1234", 1234L));
        GithubProfileResponse response = new GithubProfileResponse("nickname", "loginName", "1234",
                "test.com");
        when(githubOauthClient.requestGithubProfile(any())).thenReturn(response);
        when(jwtTokenProvider.createToken(any(), any())).thenReturn("jwt.token.here");

        TokenResponse result = hostAuthService.createToken(new TokenRequest("code"));

        assertThat(result)
                .extracting("token", "existHost")
                .containsExactly("jwt.token.here", true);
    }
}
