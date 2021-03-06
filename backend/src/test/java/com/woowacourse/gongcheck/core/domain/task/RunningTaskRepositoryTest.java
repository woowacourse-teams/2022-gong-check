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
import java.util.stream.Collectors;
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
        class 입력받은_RunningTask를_저장할_때 {

            private RunningTask runningTask;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                Task task = taskRepository.save(Task_생성(section, "책상 청소"));
                runningTask = RunningTask_생성(task.getId(), false);
            }

            @Test
            void RunningTask를_저장한다() {
                LocalDateTime timeThatBeforeSave = LocalDateTime.now();
                RunningTask actual = runningTaskRepository.save(runningTask);

                assertThat(actual.getCreatedAt()).isAfter(timeThatBeforeSave);
            }
        }
    }

    @Nested
    class existsByTaskIdIn_메소드는 {

        @Nested
        class RunningTask가_존재하는_TaskIds를_입력받는_경우 {

            private List<Long> taskIds;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                Task task = Task_생성(section, "책상 청소");
                taskIds = taskRepository.saveAll(List.of(task))
                        .stream()
                        .map(Task::getId)
                        .collect(Collectors.toList());
                runningTaskRepository.save(RunningTask_생성(task.getId(), true));
            }

            @Test
            void True를_반환한다() {
                boolean actual = runningTaskRepository.existsByTaskIdIn(taskIds);
                assertThat(actual).isTrue();
            }
        }

        @Nested
        class RunningTask가_존재하지_않는_TaskIds를_입력받는_경우 {

            private List<Long> taskIds;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                taskIds = taskRepository.saveAll(List.of(Task_생성(section, "책상 청소")))
                        .stream()
                        .map(Task::getId)
                        .collect(Collectors.toList());
            }

            @Test
            void False를_반환한다() {
                boolean result = runningTaskRepository.existsByTaskIdIn(taskIds);
                assertThat(result).isFalse();
            }
        }
    }

    @Nested
    class findByTaskId_메소드는 {

        @Nested
        class 존재하는_RunningTask의_taskId를_입력받은_경우 {

            private Long taskId;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                taskId = taskRepository.save(Task_생성(section, "책상 청소"))
                    .getId();
                runningTaskRepository.save(RunningTask_생성(taskId, false));
            }

            @Test
            void RunningTask를_반환한다() {
                Optional<RunningTask> actual = runningTaskRepository.findByTaskId(taskId);

                assertThat(actual).isNotEmpty();
            }

        }

        @Nested
        class 존재하지_않는_RunningTask의_taskId를_입력받은_경우 {

            private Long taskId;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                taskId = taskRepository.save(Task_생성(section, "책상 청소"))
                        .getId();
            }

            @Test
            void 빈_값이_반환된다() {
                Optional<RunningTask> result = runningTaskRepository.findByTaskId(taskId);

                assertThat(result).isEmpty();
            }
        }
    }
}
