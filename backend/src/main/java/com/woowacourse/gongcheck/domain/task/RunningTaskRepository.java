package com.woowacourse.gongcheck.domain.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningTaskRepository extends JpaRepository<RunningTask, Long> {
}
