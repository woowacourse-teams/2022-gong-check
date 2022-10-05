package com.woowacourse.gongcheck.core.domain.task;

import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

public interface RunningTaskRepository extends JpaRepository<RunningTask, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    boolean existsByTaskIdIn(@Param("taskIds") final List<Long> taskIds);

    Optional<RunningTask> findByTaskId(final Long taskId);

}
