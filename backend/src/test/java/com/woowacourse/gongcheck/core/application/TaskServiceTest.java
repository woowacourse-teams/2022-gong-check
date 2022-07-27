package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.core.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.section.SectionRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.TaskRepository;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("TaskService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

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

    @Autowired
    private EntityManager entityManager;

    @Nested
    class createNewRunningTasks_메소드는 {

        private Host host;
        private Job job;
        private List<Long> taskIds;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            Space space = spaceRepository.save(Space_생성(host, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
            List<Task> tasks = List.of(
                    Task_생성(section, "책상 청소"),
                    Task_생성(section, "의자 넣기")
            );
            taskRepository.saveAll(tasks);
            taskIds = tasks.stream().map(Task::getId).collect(toList());
        }

        @Nested
        class 존재하는_Host의_Space의_Job의_Section의_Task로 {

            @BeforeEach
            void setUp() {
                taskService.createNewRunningTasks(host.getId(), job.getId());
            }

            @Test
            void RunningTask를_생성한다() {
                List<RunningTask> result = runningTaskRepository.findAllById(taskIds);

                assertThat(result).hasSize(2);
            }
        }

        @Nested
        class 존재하지_않는_Host로_RunningTask를_생성할_경우 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(0L, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Task로_RunningTask를_생성할_경우 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), 0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Task로_RunningTask를_생성할_경우 {

            private Host anotherHost;

            @BeforeEach
            void setUp() {
                anotherHost = hostRepository.save(Host_생성("1234", 2345L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(anotherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class RunningTask가_이미_존재할_때_새로운_RunningTask를_생성할_경우 {

            @BeforeEach
            void setUp() {
                List<RunningTask> runningTasks = taskIds.stream()
                        .map(id -> RunningTask_생성(id, true))
                        .collect(toList());
                runningTaskRepository.saveAll(runningTasks);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.");
            }
        }
    }

    @Nested
    class isJobActivated_메소드는 {

        private Host host;
        private Job job;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            Space space = spaceRepository.save(Space_생성(host, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
            taskRepository.saveAll(List.of(
                    Task_생성(section, "책상 청소"),
                    Task_생성(section, "의자 넣기")
            ));
        }

        @Nested
        class RunningTask가_존재하는_경우 {

            @BeforeEach
            void setUp() {
                taskService.createNewRunningTasks(host.getId(), job.getId());
            }

            @Test
            void True를_반환한다() {
                JobActiveResponse result = taskService.isJobActivated(host.getId(), job.getId());

                assertThat(result.isActive()).isTrue();
            }
        }

        @Nested
        class RunningTask가_존재하지_않는_경우 {

            @Test
            void False을_반환한다() {
                JobActiveResponse result = taskService.isJobActivated(host.getId(), job.getId());

                assertThat(result.isActive()).isFalse();
            }
        }

        @Nested
        class 존재하지_않는_Host로_확인하려는_경우 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.isJobActivated(0L, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Task로_확인하려는_경우 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.isJobActivated(host.getId(), 0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Task로_확인하려는_경우 {

            private Host anotherHost;

            @BeforeEach
            void setUp() {
                anotherHost = hostRepository.save(Host_생성("1234", 2345L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.isJobActivated(anotherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class findRunningTasks_메소드는 {

        private Host host;
        private Job job;
        private List<RunningTask> runningTasks;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            Space space = spaceRepository.save(Space_생성(host, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
            List<Task> tasks = List.of(
                    Task_생성(section, "책상 청소"),
                    Task_생성(section, "의자 넣기")
            );
            taskRepository.saveAll(tasks);
            runningTasks = tasks.stream()
                    .map(task -> RunningTask_생성(task.getId(), false))
                    .collect(toList());
        }

        @Nested
        class RunningTasks가_생성되었을_때 {

            @BeforeEach
            void setUp() {
                runningTaskRepository.saveAll(runningTasks);
                entityManager.flush();
                entityManager.clear();
            }

            @Test
            void 정상적으로_RunningTask를_조회한다() {
                RunningTasksResponse result = taskService.findRunningTasks(host.getId(), job.getId());
                assertThat(result.getSections()).hasSize(1);
            }
        }

        @Nested
        class RunningTasks가_생성되지_않았을_때_조회하면 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("현재 진행중인 작업이 존재하지 않아 조회할 수 없습니다");
            }
        }

        @Nested
        class 존재하지_않는_Host로_RunningTask를_조회하면 {

            @BeforeEach
            void setUp() {
                runningTaskRepository.saveAll(runningTasks);
                entityManager.flush();
                entityManager.clear();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findRunningTasks(0L, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Task로_RunningTask를_조회하면 {

            @BeforeEach
            void setUp() {
                runningTaskRepository.saveAll(runningTasks);
                entityManager.flush();
                entityManager.clear();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findRunningTasks(host.getId(), 0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Task의_RunningTask를_조회하면 {

            private Host anotherHost;

            @BeforeEach
            void setUp() {
                runningTaskRepository.saveAll(runningTasks);
                entityManager.flush();
                entityManager.clear();

                anotherHost = hostRepository.save(Host_생성("1234", 2345L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findRunningTasks(anotherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class flipRunningTask_메소드는 {

        private Host host;
        private Task task;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            Space space = spaceRepository.save(Space_생성(host, "잠실"));
            Job job = jobRepository.save(Job_생성(space, "청소"));
            Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
            task = taskRepository.save(Task_생성(section, "책상 청소"));
        }

        @Nested
        class RunningTask가_체크되지_않았을_경우 {

            private RunningTask runningTask;

            @BeforeEach
            void setUp() {
                runningTask = runningTaskRepository.save(RunningTask_생성(task.getId(), false));
            }

            @Test
            void 체크상태를_True로_변경한다() {
                taskService.flipRunningTask(host.getId(), task.getId());

                assertThat(runningTask.isChecked()).isTrue();
            }
        }

        @Nested
        class RunningTask가_체크되었을_경우 {

            private RunningTask runningTask;

            @BeforeEach
            void setUp() {
                runningTask = runningTaskRepository.save(RunningTask_생성(task.getId(), true));
            }

            @Test
            void 체크상태를_False로_변경한다() {
                taskService.flipRunningTask(host.getId(), task.getId());

                assertThat(runningTask.isChecked()).isFalse();
            }
        }

        @Nested
        class Host가_존재하지_않으면 {

            @BeforeEach
            void setUp() {
                runningTaskRepository.save(RunningTask_생성(task.getId(), true));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(0L, task.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class RunningTask가_존재하지_않으면 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(host.getId(), task.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("현재 진행 중인 작업이 아닙니다.");
            }
        }

        @Nested
        class 다른_Host의_RunningTask의_체크상태를_변경하면 {

            private Host anotherHost;

            @BeforeEach
            void setUp() {
                anotherHost = hostRepository.save(Host_생성("1234", 2345L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(anotherHost.getId(), task.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class findTasks_메소드는 {

        private Host host;
        private Job job;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            Space space = spaceRepository.save(Space_생성(host, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
            taskRepository.saveAll(List.of(
                    Task_생성(section, "책상 청소"),
                    Task_생성(section, "의자 넣기")
            ));
        }

        @Nested
        class 존재하는_Host와_Job으로_조회하면 {

            @Test
            void 조회에_성공한다() {
                TasksResponse result = taskService.findTasks(host.getId(), job.getId());

                assertThat(result.getSections()).hasSize(1);
            }
        }

        @Nested
        class 존재하지_않는_Host로_Task를_조회하면 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findTasks(0L, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job으로_Task를_조회하면 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findTasks(host.getId(), 0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Job으로_Task를_조회하면 {

            private Host anotherHost;

            @BeforeEach
            void setUp() {
                anotherHost = hostRepository.save(Host_생성("1234", 2345L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findTasks(anotherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }
    }
}
