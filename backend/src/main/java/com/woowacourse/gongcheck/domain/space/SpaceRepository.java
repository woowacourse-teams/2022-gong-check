package com.woowacourse.gongcheck.domain.space;

import com.woowacourse.gongcheck.domain.member.Member;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    Slice<Space> findByMember(final Member member, final Pageable pageable);

    Optional<Space> findByMemberAndId(final Member member, final Long id);
}
