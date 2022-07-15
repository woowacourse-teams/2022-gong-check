package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.presentation.Authority.HOST;

import com.woowacourse.gongcheck.application.response.GithubProfileResponse;
import com.woowacourse.gongcheck.application.response.TokenResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.presentation.request.TokenRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HostAuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final GithubOauthClient githubOauthClient;
    private final HostRepository hostRepository;

    public HostAuthService(final JwtTokenProvider jwtTokenProvider,
                           final GithubOauthClient githubOauthClient,
                           final HostRepository hostRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.githubOauthClient = githubOauthClient;
        this.hostRepository = hostRepository;
    }

    @Transactional
    public TokenResponse createToken(final TokenRequest request) {
        String accessToken = githubOauthClient.requestAccessToken(request.getCode());
        GithubProfileResponse githubProfileResponse = githubOauthClient.requestGithubProfile(accessToken);
        boolean existHost = hostRepository.existsByGithubId(githubProfileResponse.getGithubId());
        Host host = findOrCreateHost(existHost, githubProfileResponse);
        String token = jwtTokenProvider.createToken(String.valueOf(host.getId()), HOST);
        return TokenResponse.of(token, existHost);
    }

    private Host findOrCreateHost(final boolean existHost, final GithubProfileResponse githubProfileResponse) {
        if (existHost) {
            return hostRepository.getByGithubId(githubProfileResponse.getGithubId());
        }
        return hostRepository.save(githubProfileResponse.toHost());
    }
}
