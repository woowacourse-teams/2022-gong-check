package com.woowacourse.gongcheck.domain.task;

import com.woowacourse.gongcheck.domain.job.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllBySectionJob(final Job job);
}
