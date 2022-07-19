package com.woowacourse.gongcheck.domain.submission;

import com.woowacourse.gongcheck.domain.job.Job;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query(value = "SELECT sub FROM Submission sub JOIN FETCH sub.job WHERE sub.job in :jobs")
    Slice<Submission> findAllByJobIn(@Param("jobs") final List<Job> jobs, Pageable pageable);
}
