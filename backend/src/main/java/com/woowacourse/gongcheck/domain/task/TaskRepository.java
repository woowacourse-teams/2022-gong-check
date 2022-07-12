package com.woowacourse.gongcheck.domain.task;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph(attributePaths = {"runningTask"}, type = EntityGraphType.FETCH)
    List<Task> findAllBySectionJob(final Job job);

    Optional<Task> findBySectionJobSpaceHostAndId(final Host host, final Long id);

    default Task getBySectionJobSpaceHostAndId(final Host host, final Long id) throws NotFoundException {
        return findBySectionJobSpaceHostAndId(host, id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 작업입니다."));
    }
}
