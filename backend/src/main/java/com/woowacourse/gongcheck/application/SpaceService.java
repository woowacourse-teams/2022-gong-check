package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.member.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final SpaceRepository spaceRepository;

    public SpaceService(final MemberRepository memberRepository, final SpaceRepository spaceRepository) {
        this.memberRepository = memberRepository;
        this.spaceRepository = spaceRepository;
    }

    public SpacesResponse findPage(final Long hostId, final Pageable pageable) {
        Member host = memberRepository.findById(hostId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
        Slice<Space> spaces = spaceRepository.findByMember(host, pageable);
        return SpacesResponse.from(spaces);
    }
}
