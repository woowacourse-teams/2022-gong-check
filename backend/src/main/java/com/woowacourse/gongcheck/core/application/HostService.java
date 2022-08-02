package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.auth.application.EnterCodeProvider;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.host.SpacePassword;
import com.woowacourse.gongcheck.core.presentation.request.SpacePasswordChangeRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HostService {

    private final HostRepository hostRepository;
    private final EnterCodeProvider enterCodeProvider;

    public HostService(HostRepository hostRepository, EnterCodeProvider enterCodeProvider) {
        this.hostRepository = hostRepository;
        this.enterCodeProvider = enterCodeProvider;
    }

    @Transactional
    public void changeSpacePassword(final Long hostId, final SpacePasswordChangeRequest request) {
        Host host = hostRepository.getById(hostId);
        host.changeSpacePassword(new SpacePassword(request.getPassword()));
    }

    public String createEnterCode(final Long hostId) {
        Host host = hostRepository.getById(hostId);
        return enterCodeProvider.createEnterCode(host.getId());
    }
}
