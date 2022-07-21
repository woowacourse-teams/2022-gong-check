package com.woowacourse.gongcheck.domain.task;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.section.SectionRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
class RunningTaskRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RunningTaskRepository runningTaskRepository;

    @Test
    void RunningTask_저장_시_생성시간이_저장된다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = taskRepository.save(Task_생성(section, "책상 청소"));

        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        RunningTask runningTask = runningTaskRepository.save(RunningTask.builder()
                .taskId(task.getId())
                .build());
        assertThat(runningTask.getCreatedAt()).isAfter(nowLocalDateTime);
    }

    @Nested
    class RunningTask_조회_및_존재_확인 {

        private Host host;
        private Space space;
        private Job job;
        private Section section;
        private Task task;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            space = spaceRepository.save(Space_생성(host, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            section = sectionRepository.save(Section_생성(job, "트랙룸"));
            task = taskRepository.save(Task_생성(section, "책상 청소"));
        }

        @Test
        void RunningTask가_존재하는_경우_True를_반환한다() {
            runningTaskRepository.save(RunningTask_생성(task.getId(), false));
            boolean result = runningTaskRepository.existsByTaskIdIn(List.of(task.getId()));

            assertThat(result).isTrue();
        }

        @Test
        void RunningTask가_존재하지_않는_경우_False를_반환한다() {
            boolean result = runningTaskRepository.existsByTaskIdIn(List.of(task.getId()));

            assertThat(result).isFalse();
        }

        @Test
        void RunningTask가_존재하지_않을_때_조회하면_빈_값이_반환된다() {
            Optional<RunningTask> result = runningTaskRepository.findByTaskId(task.getId());

            assertThat(result).isEmpty();
        }

        @Test
        void RunningTask를_조회한다() {
            runningTaskRepository.save(RunningTask_생성(task.getId(), false));
            Optional<RunningTask> result = runningTaskRepository.findByTaskId(task.getId());

            assertThat(result).isNotEmpty();
        }
    }
}
