package com.woowacourse.gongcheck.core.domain.job;

import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.exception.ErrorCode;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllBySpaceHostAndSpace(final Host host, final Space space);

    Optional<Job> findBySpaceHostAndId(final Host host, final Long id);

    List<Job> findAllBySpace(final Space space);

    default Job getBySpaceHostAndId(final Host host, final Long id) throws NotFoundException {
        return findBySpaceHostAndId(host, id)
                .orElseThrow(() -> {
                    String message = String.format("존재하지 않는 작업입니다. hostId = %d, jobId = %d", host.getId(), id);
                    throw new NotFoundException(message, ErrorCode.J001);
                });
    }

    default Job getById(final Long id) throws NotFoundException {
        return findById(id)
                .orElseThrow(() -> {
                    String message = String.format("존재하지 않는 Job입니다. jobId = %d", id);
                    throw new NotFoundException(message, ErrorCode.J002);
                });
    }
}
