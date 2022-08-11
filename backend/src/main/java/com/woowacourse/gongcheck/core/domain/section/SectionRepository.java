package com.woowacourse.gongcheck.core.domain.section;

import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {

    List<Section> findAllByJobIn(final List<Job> jobs);

    List<Section> findAllByJob(final Job job);

    Optional<Section> findByJobSpaceHostAndId(final Host host, final Long id);

    default Section getByJobSpaceHostAndId(final Host host, final Long id) throws NotFoundException {
        return findByJobSpaceHostAndId(host, id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 구역입니다."));
    }

    default Section getById(final Long id) throws NotFoundException {
        return findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 구역입니다"));
    }
}
