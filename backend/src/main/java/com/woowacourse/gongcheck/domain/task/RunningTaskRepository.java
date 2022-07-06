package com.woowacourse.gongcheck.domain.task;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RunningTaskRepository extends JpaRepository<RunningTask, Long> {

    boolean existsByTaskIdIn(@Param("taskIds") final List<Long> taskIds);
}
