package com.woowacourse.gongcheck.domain.job;

import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.space.Space;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Slice<Job> findBySpaceMemberAndSpace(final Member member, final Space space, final Pageable pageable);
}
