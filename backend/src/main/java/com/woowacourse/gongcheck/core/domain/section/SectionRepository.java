package com.woowacourse.gongcheck.core.domain.section;

import com.woowacourse.gongcheck.core.domain.job.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findAllByJobIn(final List<Job> jobs);

    List<Section> findAllByJob(final Job job);
}
