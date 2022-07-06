package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SpaceService {

    private final HostRepository hostRepository;
    private final SpaceRepository spaceRepository;

    public SpaceService(final HostRepository hostRepository, final SpaceRepository spaceRepository) {
        this.hostRepository = hostRepository;
        this.spaceRepository = spaceRepository;
    }

    public SpacesResponse findPage(final Long hostId, final Pageable pageable) {
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
        Slice<Space> spaces = spaceRepository.findByHost(host, pageable);
        return SpacesResponse.from(spaces);
    }
}
