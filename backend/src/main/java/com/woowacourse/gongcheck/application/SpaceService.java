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
    private final ImageUploader imageUploader;

    public SpaceService(HostRepository hostRepository, SpaceRepository spaceRepository, ImageUploader imageUploader) {
        this.hostRepository = hostRepository;
        this.spaceRepository = spaceRepository;
        this.imageUploader = imageUploader;
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

        String imageUrl = imageUploader.upload(request.getImage(), "spaces");

        Space space = Space.builder()
                .host(host)
                .name(request.getName())
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build();
        return spaceRepository.save(space).getId();
    }
}
