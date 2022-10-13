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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.woowacourse.gongcheck.ApplicationTest;
import com.woowacourse.gongcheck.SupportRepository;
import com.woowacourse.gongcheck.core.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.application.response.TaskResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import com.woowacourse.gongcheck.core.application.response.TasksWithSectionResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskSseEmitterContainer;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@ApplicationTest
@DisplayName("TaskService 클래스의")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SupportRepository repository;

    @Autowired
    private RunningTaskRepository runningTaskRepository;

    @MockBean
    private RunningTaskSseEmitterContainer runningTaskSseEmitterContainer;

    @Nested
    class createNewRunningTasks_메소드는 {

        @Nested
        class 입력받은_Job에_Task가_존재하지_않는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job noTaskJob = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(noTaskJob, "트랙룸"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), noTaskJob.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("작업이 존재하지 않습니다.");
            }
        }

        @Nested
        class 입력받은_Host가_존재하지_않는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 입력받은_Job이_존재하지_않는_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1L));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 입력받은_Job이_입력받은_Host의_소유가_아닌_경우 {

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Host anotherHost = repository.save(Host_생성("1234", 2L));
            private final Space space = repository.save(Space_생성(anotherHost, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(
                    List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 입력받은_Job이_가진_Task의_RunningTask가_이미_존재하는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(
                    List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            private final List<RunningTask> runningTasks = repository.saveAll(tasks.stream()
                    .map(Task::getId)
                    .map(id -> RunningTask_생성(id, true))
                    .collect(toList()));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.");
            }
        }

        @Nested
        class 올바른_입력을_받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(
                    List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));

            @Test
            void Job이_가진_Task에_해당하는_RunningTask를_생성한다() {
                taskService.createNewRunningTasks(host.getId(), job.getId());

                List<Long> taskIds = tasks.stream()
                        .map(Task::getId)
                        .collect(toList());
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
    }

    @Nested
    class isJobActivated_메소드는 {

        @Nested
        class 입력받은_Job의_RunningTask가_생성되어있지_않은_경우 {

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(
                    List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));

            @Test
            void 거짓을_반환한다() {
                JobActiveResponse actual = taskService.isJobActivated(host.getId(), job.getId());

                assertThat(actual.isActive()).isFalse();
            }
        }

        @Nested
        class 존재하지_않는_Host를_입력받는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.isJobActivated(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job을_입력받는_경우 {

            private static final long NON_EXIST_JOB_ID = 1L;

            private final Host host = repository.save(Host_생성("1234", 1111L));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.isJobActivated(host.getId(), NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host가_소유한_Job을_입력받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Host anotherHost = repository.save(Host_생성("1234", 2345L));
            private final Space space = repository.save(Space_생성(anotherHost, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.isJobActivated(host.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 입력받은_Job의_RunningTask가_생성되어_있는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(List.of(
                    Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            private final List<RunningTask> runningTasks = repository.saveAll(tasks.stream()
                    .map(Task::getId)
                    .map(id -> RunningTask_생성(id, true))
                    .collect(toList()));

            @Test
            void 참을_반환한다() {
                JobActiveResponse actual = taskService.isJobActivated(host.getId(), job.getId());

                assertThat(actual.isActive()).isTrue();
            }
        }
    }

    @Nested
    class connectRunningTasks_메서드는 {

        @Nested
        class 존재하지_않는_Host를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.connectRunningTasks(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job을_입력받은_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1L));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.connectRunningTasks(host.getId(), NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host가_소유한_Job을_입력받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Host anotherHost = repository.save(Host_생성("1234", 2345L));
            private final Space space = repository.save(Space_생성(anotherHost, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(List.of(
                    Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            private final List<RunningTask> runningTasks = repository.saveAll(tasks.stream()
                    .map(task -> RunningTask_생성(task.getId(), false))
                    .collect(toList()));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.connectRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class RunningTasks가_생성되지_않은_Job을_입력받은_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(
                    List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.connectRunningTasks(host.getId(), job.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행중인 RunningTask가 없습니다");
            }
        }

        @Nested
        class 올바른_입력을_받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(List.of(
                    Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
            private final List<RunningTask> runningTasks = repository.saveAll(tasks.stream()
                    .map(task -> RunningTask_생성(task.getId(), false))
                    .collect(toList()));

            @Test
            void emitter를_생성하는_메서드를_호출한다() {
                taskService.connectRunningTasks(host.getId(), job.getId());

                verify(runningTaskSseEmitterContainer, times(1))
                        .createEmitterWithConnectionEvent(anyLong(), any());
            }
        }
    }

    @Nested
    class flipRunningTask_메소드는 {

        @Nested
        class 입력받은_Host와_Task가_존재하고_Task의_RunningTask가_존재하는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final Task task = repository.save(Task_생성(section, "책상 청소"));
            private final RunningTask runningTask = repository.save(RunningTask_생성(task.getId(), true));

            @Test
            void 체크상태를_반대로_변경한다() {
                taskService.flipRunningTask(host.getId(), task.getId());
                RunningTask actual = repository.getById(RunningTask.class, runningTask.getTaskId());

                assertThat(actual.isChecked()).isFalse();
            }
        }

        @Nested
        class 존재하지_않는_Host를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long TASK_ID = 1L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(NON_EXIST_HOST_ID, TASK_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Task를_입력받은_경우 {

            private static final long NON_EXIST_TASK_ID = 0L;

            private Host host = repository.save(Host_생성("1234", 1L));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(host.getId(), NON_EXIST_TASK_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Task를_입력받은_경우 {

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Host anotherHost = repository.save(Host_생성("1234", 2L));
            private final Space space = repository.save(Space_생성(anotherHost, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final Task task = repository.save(Task_생성(section, "책상 청소"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(host.getId(), task.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 입력받은_Task의_RunningTask가_존재하지_않는_경우 {

            private Host host = repository.save(Host_생성("1234", 1L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final Task task = repository.save(Task_생성(section, "책상 청소"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(host.getId(), task.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행 중인 작업이 아닙니다.");
            }
        }
    }

    @Nested
    class findTasks_메소드는 {

        @Nested
        class Host와_Job을_입력받는_경우 {

            private static final String SECTION_NAME = "트랙룸";
            private static final String TASK_NAME_1 = "책상 청소";
            private static final String TASK_NAME_2 = "의자 넣기";

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, SECTION_NAME));
            private final List<Task> tasks = repository.saveAll(
                    List.of(Task_생성(section, TASK_NAME_1), Task_생성(section, TASK_NAME_2)));

            @Test
            void Job의_Task를_반환한다() {
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
        class 입력받은_Host가_존재하지_않는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.findTasks(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 입력받은_Job이_존재하지_않는_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1L));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.findTasks(host.getId(), NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Job을_입력받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Host anotherHost = repository.save(Host_생성("1234", 2L));
            private final Space space = repository.save(Space_생성(anotherHost, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(
                    List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.findTasks(host.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class checkRunningTasksInSection_메소드는 {

        @Nested
        class Host와_Section을_입력받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final Task task1 = repository.save(Task_생성(section, "책상 청소"));
            private final Task task2 = repository.save(Task_생성(section, "의자 정리"));
            private final List<RunningTask> runningTasks = repository.saveAll(
                    List.of(RunningTask_생성(task1.getId(), false),
                            RunningTask_생성(task2.getId(), false)));

            @Test
            void 해당_Section의_RunningTask를_모두_체크싱테로_변경한다() {
                taskService.checkRunningTasksInSection(host.getId(), section.getId());
                List<RunningTask> actual = repository.findAll(RunningTask.class);

                assertThat(actual).extracting("isChecked")
                        .containsExactly(true, true);
            }

            @Test
            void emitterContainer에게_event_전송을_요청한다() {
                taskService.checkRunningTasksInSection(host.getId(), section.getId());

                verify(runningTaskSseEmitterContainer, times(1))
                        .publishFlipEvent(eq(job.getId()), any());
            }
        }

        @Nested
        class 입력받은_Host가_존재하지_않는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.checkRunningTasksInSection(NON_EXIST_HOST_ID, JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 입력받은_Section이_존재하지_않는_경우 {

            private static final long NON_EXIST_SECTION_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1L));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.checkRunningTasksInSection(host.getId(), NON_EXIST_SECTION_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 구역입니다.");
            }
        }

        @Nested
        class 다른_Host의_Section을_입력받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Host anotherHost = repository.save(Host_생성("1234", 2345L));
            private final Space space = repository.save(Space_생성(anotherHost, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.checkRunningTasksInSection(host.getId(), section.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 구역입니다.");
            }
        }

        @Nested
        class 입력받은_Section에_RunningTask가_존재하지_않는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final List<Task> tasks = repository.saveAll(
                    List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.checkRunningTasksInSection(host.getId(), section.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행중인 RunningTask가 없습니다");
            }
        }
    }

    @Nested
    class flip_메서드는 {

        @Nested
        class 입력받은_TaskId가_존재하지_않는_경우 {

            private static final long NON_EXIST_TASK_ID = 0L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(NON_EXIST_TASK_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 Task입니다");
            }
        }

        @Nested
        class 입력받은_Task의_RunningTaks가_아직_생성되지_않은_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "굿샷강의장"));
            private final Task task = repository.save(Task_생성(section, "책상 닦기"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.flipRunningTask(task.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행 중인 작업이 아닙니다");
            }
        }

        @Nested
        class TaskId를_입력받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final Task task = repository.save(Task_생성(section, "책상 청소"));
            private final RunningTask runningTask = repository.save(RunningTask_생성(task.getId(), false));

            @Test
            void 해당_Task와_일치하는_RunningTask의_상태를_반대로_변경한다() {
                taskService.flipRunningTask(task.getId());
                RunningTask actual = repository.getById(RunningTask.class, runningTask.getTaskId());
                assertThat(actual.isChecked()).isTrue();
            }

        }
    }

    @Nested
    class showRunningTasks_메서드는 {
        @Nested
        class 입력받은_JobId가_존재하지_않는_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.showRunningTasks(NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 Job입니다");
            }
        }

        @Nested
        class 입력받은_Job에_진행중인_RunningTask가_존재하지_않는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final Task task = repository.save(Task_생성(section, "책상 청소"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> taskService.showRunningTasks(job.getId()))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("현재 진행중인 RunningTask가 없습니다");
            }
        }

        @Nested
        class JobId를_입력받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final Section section = repository.save(Section_생성(job, "트랙룸"));
            private final Task task = repository.save(Task_생성(section, "책상 청소"));
            private final RunningTask runningTask = repository.save(RunningTask_생성(task.getId(), false));

            @Test
            void 해당_Job에_속한_RunningTask_응답을_반환한다() {
                RunningTasksResponse actual = taskService.showRunningTasks(job.getId());
                assertThat(actual).isNotNull();
                assertThat(actual.getSections()).hasSize(1);
            }
        }
    }
}
