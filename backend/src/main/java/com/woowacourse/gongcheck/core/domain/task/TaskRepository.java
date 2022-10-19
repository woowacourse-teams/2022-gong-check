package com.woowacourse.gongcheck.core.domain.task;

import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.exception.ErrorCode;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(final Long id);

    @Query("select t from Task t left join fetch t.runningTask join fetch t.section s join fetch s.job j where j = :job")
    List<Task> findAllBySectionJob(@Param("job") final Job job);

    Optional<Task> findBySectionJobSpaceHostAndId(final Host host, final Long id);

    @Query("select t from Task t left join fetch t.runningTask where t.section in (:sections)")
    List<Task> findAllBySectionIn(@Param("sections") final List<Section> sections);

    void deleteAllBySectionIn(final List<Section> sections);

    @EntityGraph(attributePaths = {"runningTask"}, type = EntityGraphType.FETCH)
    List<Task> findAllBySection(final Section section);

    default Task getBySectionJobSpaceHostAndId(final Host host, final Long id) throws NotFoundException {
        return findBySectionJobSpaceHostAndId(host, id)
                .orElseThrow(() -> {
                    String message = String.format("존재하지 않는 작업입니다. hostId = %d, taskId = %d", host.getId(), id);
                    throw new NotFoundException(message, ErrorCode.T003);
                });
    }

    default Task getById(final Long id) {
        return findById(id).orElseThrow(() -> {
            String message = String.format("존재하지 않는 Task입니다. taskId = %d", id);
            throw new NotFoundException(message, ErrorCode.T004);
        });
    }
}
