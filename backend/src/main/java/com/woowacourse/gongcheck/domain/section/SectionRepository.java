package com.woowacourse.gongcheck.domain.section;

import com.woowacourse.gongcheck.domain.job.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findAllByJobIn(final List<Job> jobs);
}
