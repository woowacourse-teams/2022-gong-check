package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.host.SpacePassword;
import com.woowacourse.gongcheck.presentation.request.SpacePasswordChangeRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HostService {

    private final HostRepository hostRepository;

    public HostService(final HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    @Transactional
    public void changeSpacePassword(final Long hostId, final SpacePasswordChangeRequest request) {
        Host host = hostRepository.getById(hostId);
        host.changeSpacePassword(new SpacePassword(request.getPassword()));
    }
}
