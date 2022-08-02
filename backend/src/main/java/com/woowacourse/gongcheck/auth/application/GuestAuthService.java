package com.woowacourse.gongcheck.auth.application;

import static com.woowacourse.gongcheck.auth.domain.Authority.GUEST;

import com.woowacourse.gongcheck.auth.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.host.SpacePassword;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GuestAuthService {

    private final HostRepository hostRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public GuestAuthService(final HostRepository hostRepository, final JwtTokenProvider jwtTokenProvider) {
        this.hostRepository = hostRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public GuestTokenResponse createToken(final Long hostId, final GuestEnterRequest request) {
        Host host = hostRepository.getById(hostId);
        host.checkPassword(new SpacePassword(request.getPassword()));

        String token = jwtTokenProvider.createToken(String.valueOf(hostId), GUEST);
        return GuestTokenResponse.from(token);
    }
}
