package com.woowacourse.gongcheck.core.domain.task;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.section.SectionRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
@DisplayName("RunningTaskRepository 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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
    class save_메소드는 {

        @Nested
        class 존재하는_Host의_Space의_Job의_Section의_Task로 {

            private Task task;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                task = taskRepository.save(Task_생성(section, "책상 청소"));
            }

            @Test
            void RunningTask를_저장한다() {
                RunningTask runningTask = runningTaskRepository.save(RunningTask_생성(task.getId(), false));

                assertThat(runningTask.getCreatedAt()).isBefore(LocalDateTime.now());
            }
        }
    }

    @Nested
    class existsByTaskIdIn_메소드는 {

        private Task task;

        @BeforeEach
        void setUp() {
            Host host = hostRepository.save(Host_생성("1234", 1234L));
            Space space = spaceRepository.save(Space_생성(host, "잠실"));
            Job job = jobRepository.save(Job_생성(space, "청소"));
            Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
            task = taskRepository.save(Task_생성(section, "책상 청소"));
        }

        @Nested
        class TaskId에_해당하는_RunningTask가_존재할_때 {

            @BeforeEach
            void setUp() {
                runningTaskRepository.save(RunningTask_생성(task.getId(), false));
            }

            @Test
            void True를_반환한다() {
                boolean result = runningTaskRepository.existsByTaskIdIn(List.of(task.getId()));

                assertThat(result).isTrue();
            }
        }

        @Nested
        class TaskId에_해당하는_RunningTask가_존재하지_않는_경우 {

            @Test
            void False를_반환한다() {
                boolean result = runningTaskRepository.existsByTaskIdIn(List.of(task.getId()));

                assertThat(result).isFalse();
            }
        }
    }

    @Nested
    class findByTaskId_메소드는 {

        private Task task;

        @BeforeEach
        void setUp() {
            Host host = hostRepository.save(Host_생성("1234", 1234L));
            Space space = spaceRepository.save(Space_생성(host, "잠실"));
            Job job = jobRepository.save(Job_생성(space, "청소"));
            Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
            task = taskRepository.save(Task_생성(section, "책상 청소"));
        }

        @Nested
        class RunningTask가_존재하면 {

            @BeforeEach
            void setUp() {
                runningTaskRepository.save(RunningTask_생성(task.getId(), false));
            }

            @Test
            void RunningTask를_조회한다() {
                Optional<RunningTask> result = runningTaskRepository.findByTaskId(task.getId());

                assertThat(result).isNotEmpty();
            }

        }

        @Nested
        class RunningTask가_존재하지_않을_때 {

            @Test
            void 조회_시_빈_값이_반환된다() {
                Optional<RunningTask> result = runningTaskRepository.findByTaskId(task.getId());

                assertThat(result).isEmpty();
            }
        }
    }
}
