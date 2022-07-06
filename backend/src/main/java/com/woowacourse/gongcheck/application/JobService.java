package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.JobsResponse;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
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
public class JobService {

    private final MemberRepository memberRepository;
    private final SpaceRepository spaceRepository;
    private final JobRepository jobRepository;

    public JobService(final MemberRepository memberRepository, final SpaceRepository spaceRepository,
                      final JobRepository jobRepository) {
        this.memberRepository = memberRepository;
        this.spaceRepository = spaceRepository;
        this.jobRepository = jobRepository;
    }

    public JobsResponse findPage(final Long hostId, final Long spaceId, final Pageable pageable) {
        Member host = memberRepository.findById(hostId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
        Space space = spaceRepository.findByMemberAndId(host, spaceId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 공간입니다."));
        Slice<Job> jobs = jobRepository.findBySpaceMemberAndSpace(host, space, pageable);
        return JobsResponse.from(jobs);
    }
}
