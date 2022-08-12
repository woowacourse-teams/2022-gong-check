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
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.ApplicationTest;
import com.woowacourse.gongcheck.SupportRepository;
import com.woowacourse.gongcheck.core.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTaskResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksWithSectionResponse;
import com.woowacourse.gongcheck.core.application.response.TaskResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import com.woowacourse.gongcheck.core.application.response.TasksWithSectionResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.SseEmitterRepository;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("TaskService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SupportRepository repository;

    @Autowired
    private RunningTaskRepository runningTaskRepository;

    @Autowired
    private SseEmitterRepository sseEmitterRepository;

    @Nested
    class createNewRunningTasks_메소드는 {

        @Nested
        class 존재하는_Host와_Job을_입력받는_경우 {

            private Host host;
            private Job job;
            private List<Long> taskIds;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                List<Task> tasks = repository.saveAll(
                        List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
                taskIds = tasks.stream()
                        .map(Task::getId)
                        .collect(toList());
            }

            @Test
            void Job이_가진_Task에_해당하는_RunningTask를_생성한다() {
                taskService.createNewRunningTasks(host.getId(), job.getId());
                List<RunningTask> actual = runningTaskRepository.findAllById(taskIds);

                assertAll(
                        () -> assertThat(actual)
                                .extracting(RunningTask::isChecked)
                                .containsExactly(false, false),
                        () -> assertThat(actual)
                                .extracting(RunningTask::getTaskId)
                                .containsAll(taskIds)
                );
            }
        }

        @Nested
        class Task가_존재하지_않는_경우 {

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                repository.save(Section_생성(job, "트랙룸"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("작업이 존재하지 않습니다.");
            }
        }

        @Nested
        class 존재하지_않는_Host로_RunningTask를_생성할_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Task로_RunningTask를_생성할_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private Long hostId;

            @BeforeEach
            void setUp() {
                hostId = repository.save(Host_생성("1234", 1234567L))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(hostId, NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Task로_RunningTask를_생성할_경우 {

            private Host anotherHost;
            private Job job;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                anotherHost = repository.save(Host_생성("1234", 2345L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                repository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(anotherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class RunningTask가_이미_존재할_때_새로운_RunningTask를_생성할_경우 {

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                List<Task> tasks = repository.saveAll(
                        List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
                repository.saveAll(tasks.stream()
                        .map(Task::getId)
                        .map(id -> RunningTask_생성(id, true))
                        .collect(toList()));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.");
            }
        }
    }

    @Nested
    class isJobActivated_메소드는 {

        @Nested
        class 존재하는_Host와_Job의_RunningTask가_존재하는_경우 {

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                List<Task> tasks = repository.saveAll(List.of(
                        Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
                repository.saveAll(tasks.stream()
                        .map(Task::getId)
                        .map(id -> RunningTask_생성(id, true))
                        .collect(toList()));
            }

            @Test
            void True를_반환한다() {
                JobActiveResponse actual = taskService.isJobActivated(host.getId(), job.getId());

                assertThat(actual.isActive()).isTrue();
            }
        }

        @Nested
        class 존재하는_Host와_Job의_RunningTask가_존재하지_않는_경우 {

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                repository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            }

            @Test
            void False을_반환한다() {
                JobActiveResponse actual = taskService.isJobActivated(host.getId(), job.getId());

                assertThat(actual.isActive()).isFalse();
            }
        }

        @Nested
        class 존재하지_않는_Host로_확인하려는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.isJobActivated(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Task로_확인하려는_경우 {

            private static final long NON_EXIST_JOB_ID = 1L;

            private Long hostId;

            @BeforeEach
            void setUp() {
                hostId = repository.save(Host_생성("1234", 1111L))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.isJobActivated(hostId, NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Task로_확인하려는_경우 {

            private Host anotherHost;
            private Job job;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                anotherHost = repository.save(Host_생성("1234", 2345L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                repository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.isJobActivated(anotherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class findRunningTasks_메소드는 {

        @Nested
        class 존재하는_Host와_RunningTasks가_생성된_Job을_입력받은_경우 {

            private static final String SECTION_NAME = "트랙룸";
            private static final String TASK_NAME_1 = "책상 청소";
            private static final String TASK_NAME_2 = "의자 넣기";
            private static final int TASK_INDEX = 0;

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, SECTION_NAME));
                List<Task> tasks = repository.saveAll(List.of(
                        Task_생성(section, TASK_NAME_1), Task_생성(section, TASK_NAME_2)));
                repository.saveAll(tasks.stream()
                        .map(task -> RunningTask_생성(task.getId(), false))
                        .collect(toList()));
            }

            @Test
            void 정상적으로_RunningTasks를_조회한다() {
                List<RunningTasksWithSectionResponse> actual = taskService.findRunningTasks(host.getId(), job.getId())
                        .getSections();
                List<RunningTaskResponse> actualTasks = actual.get(TASK_INDEX)
                        .getTasks();

                assertAll(
                        () -> assertThat(actual)
                                .extracting(RunningTasksWithSectionResponse::getName)
                                .containsExactly(SECTION_NAME),
                        () -> assertThat(actual).hasSize(1),
                        () -> assertThat(actualTasks)
                                .extracting(RunningTaskResponse::getName)
                                .containsExactly(TASK_NAME_1, TASK_NAME_2)
                );
            }
        }

        @Nested
        class 존재하지_않는_Host를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findRunningTasks(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job을_입력받은_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private Long hostId;

            @BeforeEach
            void setUp() {
                hostId = repository.save(Host_생성("1234", 1111L))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findRunningTasks(hostId, NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Task의_RunningTask를_조회하면 {

            private Host anotherHost;
            private Job job;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                anotherHost = repository.save(Host_생성("1234", 2345L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                List<Task> tasks = repository.saveAll(List.of(
                        Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
                repository.saveAll(tasks.stream()
                        .map(task -> RunningTask_생성(task.getId(), false))
                        .collect(toList()));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findRunningTasks(anotherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class RunningTasks가_생성되지_않은_Job을_입력받은_경우 {

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                repository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행중인 작업이 존재하지 않아 조회할 수 없습니다");
            }
        }
    }

    @Nested
    class flipRunningTask_메소드는 {

        @Nested
        class 존재하는_Host와_체크되지_않은_RunningTask를_가진_Task를_입력받은_경우 {

            private Host host;
            private Task task;
            private Long runningTaskId;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                Job job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                task = repository.save(Task_생성(section, "책상 청소"));
                runningTaskId = repository.save(RunningTask_생성(task.getId(), false))
                        .getTaskId();
            }

            @Test
            void 체크상태를_True로_변경한다() {
                taskService.flipRunningTask(host.getId(), task.getId());
                RunningTask actual = repository.getById(RunningTask.class, runningTaskId);

                assertThat(actual.isChecked()).isTrue();
            }
        }

        @Nested
        class 존재하는_Host와_체크된_RunningTask를_가진_Task를_입력받은_경우 {

            private Host host;
            private Task task;
            private Long runningTaskId;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                Job job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                task = repository.save(Task_생성(section, "책상 청소"));
                runningTaskId = repository.save(RunningTask_생성(task.getId(), true))
                    .getTaskId();
            }

            @Test
            void 체크상태를_False로_변경한다() {
                taskService.flipRunningTask(host.getId(), task.getId());
                RunningTask actual = repository.getById(RunningTask.class, runningTaskId);

                assertThat(actual.isChecked()).isFalse();
            }
        }

        @Nested
        class 존재하지_않는_Host를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long TASK_ID = 1L;

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(NON_EXIST_HOST_ID, TASK_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Task를_입력받은_경우 {

            private static final long NON_EXIST_TASK_ID = 0L;

            private Long hostId;

            @BeforeEach
            void setUp() {
                hostId = repository.save(Host_생성("1234", 1111L))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(hostId, NON_EXIST_TASK_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Task를_입력받은_경우 {

            private Host anotherHost;
            private Long taskId;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                anotherHost = repository.save(Host_생성("1234", 2345L));
                Space space = repository.save(Space_생성(host, "잠실"));
                Job job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                taskId = repository.save(Task_생성(section, "책상 청소"))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(anotherHost.getId(), taskId))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 진행중이지_않은_Task를_입력받은_경우 {

            private Host host;
            private Long taskId;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                Job job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                taskId = repository.save(Task_생성(section, "책상 청소"))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(host.getId(), taskId))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행 중인 작업이 아닙니다.");
            }
        }
    }

    @Nested
    class findTasks_메소드는 {

        @Nested
        class 존재하는_Host와_Job을_입력하는_경우 {

            private static final String SECTION_NAME = "트랙룸";
            private static final String TASK_NAME_1 = "책상 청소";
            private static final String TASK_NAME_2 = "의자 넣기";

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, SECTION_NAME));
                repository.saveAll(List.of(Task_생성(section, TASK_NAME_1), Task_생성(section, TASK_NAME_2)));
            }

            @Test
            void 조회에_성공한다() {
                TasksResponse actual = taskService.findTasks(host.getId(), job.getId());
                TasksWithSectionResponse actualTaskWithSectionResponses = actual.getSections().get(0);
                List<TaskResponse> actualTaskResponses = actualTaskWithSectionResponses.getTasks();

                assertAll(
                        () -> assertThat(actualTaskWithSectionResponses.getName()).isEqualTo(SECTION_NAME),
                        () -> assertThat(actualTaskResponses).extracting(TaskResponse::getName)
                                .containsExactly(TASK_NAME_1, TASK_NAME_2),
                        () -> assertThat(actual.getSections()).hasSize(1)
                );
            }
        }

        @Nested
        class 존재하지_않는_Host를_입력하는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findTasks(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job을_입력하는_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private long hostId;

            @BeforeEach
            void setUp() {
                hostId = repository.save(Host_생성("1234", 1111L))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findTasks(hostId, NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Job을_입력하는_경우 {

            private Host anotherHost;
            private Job job;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                anotherHost = repository.save(Host_생성("1234", 2345L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
                Section section = repository.save(Section_생성(job, "트랙룸"));
                repository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.findTasks(anotherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class checkRunningTasksInSection_메소드는 {

        @Nested
        class 존재하는_Host와_Section을_입력할_경우 {

            private Host host;
            private Section section;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                Job job = repository.save(Job_생성(space, "청소"));
                section = repository.save(Section_생성(job, "트랙룸"));
                Task task1 = repository.save(Task_생성(section, "책상 청소"));
                Task task2 = repository.save(Task_생성(section, "의자 정리"));
                repository.saveAll(List.of(RunningTask_생성(task1.getId(), false),
                        RunningTask_생성(task2.getId(), false)));
            }

            @Test
            void 해당_Section의_RunningTask를_모두_체크한다() {
                taskService.checkRunningTasksInSection(host.getId(), section.getId());
                List<RunningTask> actual = repository.findAll(RunningTask.class);

                assertThat(actual).extracting("isChecked")
                        .containsExactly(true, true);
            }
        }

        @Nested
        class 존재하지_않는_Host를_입력하는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.checkRunningTasksInSection(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Section을_입력하는_경우 {

            private static final long NON_EXIST_SECTION_ID = 0L;

            private long hostId;

            @BeforeEach
            void setUp() {
                hostId = repository.save(Host_생성("1234", 1111L))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.checkRunningTasksInSection(hostId, NON_EXIST_SECTION_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 구역입니다.");
            }
        }

        @Nested
        class 다른_Host의_Section을_입력하는_경우 {

            private Host anotherHost;
            private Section section;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                anotherHost = repository.save(Host_생성("1234", 2345L));
                Space space = repository.save(Space_생성(host, "잠실"));
                Job job = repository.save(Job_생성(space, "청소"));
                section = repository.save(Section_생성(job, "트랙룸"));
                repository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.checkRunningTasksInSection(anotherHost.getId(), section.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 구역입니다.");
            }
        }

        @Nested
        class 해당_Section의_진행중인_RunningTask가_없는_경우 {

            private Host host;
            private Section section;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                Job job = repository.save(Job_생성(space, "청소"));
                section = repository.save(Section_생성(job, "트랙룸"));
                repository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskService.checkRunningTasksInSection(host.getId(), section.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("현재 진행중인 RunningTask가 없습니다");
            }
        }
    }
}
