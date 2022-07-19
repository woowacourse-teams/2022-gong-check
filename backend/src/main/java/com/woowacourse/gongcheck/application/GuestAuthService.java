package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.host.SpacePassword;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
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

    public GuestTokenResponse createToken(final long hostId, final GuestEnterRequest request) {
        Host host = hostRepository.getById(hostId);
        host.checkPassword(new SpacePassword(request.getPassword()));

        String token = jwtTokenProvider.createToken(String.valueOf(hostId));
        return GuestTokenResponse.from(token);
    }
}
