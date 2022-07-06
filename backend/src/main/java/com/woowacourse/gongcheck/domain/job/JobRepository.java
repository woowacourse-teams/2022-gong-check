package com.woowacourse.gongcheck.domain.job;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.space.Space;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Slice<Job> findBySpaceHostAndSpace(final Host host, final Space space, final Pageable pageable);

    Optional<Job> findBySpaceHostAndId(final Host host, final Long id);
}
