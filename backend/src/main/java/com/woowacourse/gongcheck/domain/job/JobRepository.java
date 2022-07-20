package com.woowacourse.gongcheck.domain.job;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Slice<Job> findBySpaceHostAndSpace(final Host host, final Space space, final Pageable pageable);

    Optional<Job> findBySpaceHostAndId(final Host host, final Long id);

    List<Job> findAllBySpace(final Space space);

    default Job getBySpaceHostAndId(final Host host, final Long id) throws NotFoundException {
        return findBySpaceHostAndId(host, id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 작업입니다."));
    }

    default Job getById(final Long id) throws NotFoundException {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 작업입니다."));
    }
}
