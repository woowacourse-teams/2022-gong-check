package com.woowacourse.gongcheck.core.domain.submission;

import com.woowacourse.gongcheck.core.domain.job.Job;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @EntityGraph(attributePaths = "job")
    Slice<Submission> findAllByJobIn(@Param("jobs") final List<Job> jobs, final Pageable pageable);
}
