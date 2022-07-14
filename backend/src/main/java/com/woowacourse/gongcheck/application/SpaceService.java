package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.presentation.request.SpaceCreateRequest;
import java.time.LocalDateTime;
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
        Host host = hostRepository.getById(hostId);
        Slice<Space> spaces = spaceRepository.findByHost(host, pageable);
        return SpacesResponse.from(spaces);
    }

    public Long createSpace(Long hostId, SpaceCreateRequest request) {
        Host host = hostRepository.getById(hostId);
        if (spaceRepository.existsByHostAndName(host, request.getName())) {
            throw new BusinessException("이미 존재하는 이름입니다.");
        }

        Space space = Space.builder()
                .host(host)
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .createdAt(LocalDateTime.now())
                .build();
        Space savedSpace = spaceRepository.save(space);
        return savedSpace.getId();
    }
}
