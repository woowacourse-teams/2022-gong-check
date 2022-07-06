package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
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
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
        host.checkPassword(request.getPassword());

        String token = jwtTokenProvider.createToken(String.valueOf(hostId));
        return GuestTokenResponse.from(token);
    }
}
