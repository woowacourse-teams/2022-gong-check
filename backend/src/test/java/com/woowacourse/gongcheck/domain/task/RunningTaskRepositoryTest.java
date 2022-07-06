package com.woowacourse.gongcheck.domain.task;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.section.SectionRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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

    @Nested
    class 테스크가_존재한다 {

        private Host host;
        private Space space;
        private Job job;
        private Section section;
        private Task task;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234"));
            space = spaceRepository.save(Space_생성(host, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            section = sectionRepository.save(Section_생성(job, "트랙룸"));
            task = taskRepository.save(Task_생성(section, "책상 청소"));
        }

        @Test
        void 진행중인_테스크가_존재하는_경우_True를_반환한다() {
            runningTaskRepository.save(RunningTask_생성(task));
            boolean result = runningTaskRepository.existsByTaskIdIn(List.of(task.getId()));

            assertThat(result).isTrue();
        }

        @Test
        void 진행중인_테스크가_존재하지_않는_경우_False를_반환한다() {
            boolean result = runningTaskRepository.existsByTaskIdIn(List.of(task.getId()));

            assertThat(result).isFalse();
        }

        @Test
        void 진행중인_테스크가_존재하지_않는_경우_테스크를_조회한다() {
            Optional<RunningTask> result = runningTaskRepository.findByTaskSectionJobSpaceHostAndTaskId(
                    host, task.getId());

            assertThat(result).isEmpty();
        }

        @Test
        void 진행중인_테스크가_존재하는_경우_테스크를_조회한다() {
            runningTaskRepository.save(RunningTask_생성(task));
            Optional<RunningTask> result = runningTaskRepository.findByTaskSectionJobSpaceHostAndTaskId(
                    host, task.getId());

            assertThat(result).isNotEmpty();
        }
    }
}
