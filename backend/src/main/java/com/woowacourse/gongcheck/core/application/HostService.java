package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.auth.application.EntranceCodeProvider;
import com.woowacourse.gongcheck.core.application.response.HostProfileResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.host.Nickname;
import com.woowacourse.gongcheck.core.domain.host.SpacePassword;
import com.woowacourse.gongcheck.core.presentation.request.HostProfileChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.SpacePasswordChangeRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HostService {

    private final HostRepository hostRepository;
    private final EntranceCodeProvider entranceCodeProvider;

    public HostService(final HostRepository hostRepository, final EntranceCodeProvider entranceCodeProvider) {
        this.hostRepository = hostRepository;
        this.entranceCodeProvider = entranceCodeProvider;
    }

    @Transactional
    public void changeSpacePassword(final Long hostId, final SpacePasswordChangeRequest request) {
        Host host = hostRepository.getById(hostId);
        host.changeSpacePassword(new SpacePassword(request.getPassword()));
    }

    public String createEntranceCode(final Long hostId) {
        Host host = hostRepository.getById(hostId);
        return entranceCodeProvider.createEntranceCode(host.getId());
    }

    public HostProfileResponse findProfile(final String entranceCode) {
        Long hostId = entranceCodeProvider.parseId(entranceCode);
        Host host = hostRepository.getById(hostId);
        return HostProfileResponse.from(host);
    }

    @Transactional
    public void changeProfile(final Long hostId, final HostProfileChangeRequest request) {
        Host host = hostRepository.getById(hostId);
        host.changeNickname(new Nickname(request.getNickname()));
    }
}
