package com.woowacourse.gongcheck.domain.task;

import com.woowacourse.gongcheck.domain.host.Host;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RunningTaskRepository extends JpaRepository<RunningTask, Long> {

    boolean existsByTaskIdIn(@Param("taskIds") final List<Long> taskIds);

    Optional<RunningTask> findByTaskSectionJobSpaceHostAndTaskId(final Host host, final Long taskId);
}
