package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.section.SectionRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.domain.task.RunningTask;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.domain.task.Task;
import com.woowacourse.gongcheck.domain.task.TaskRepository;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
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

    @Test
    void 존재하지_않는_호스트로_새로운_작업을_진행하려하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> taskService.createNewRunningTasks(0L, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 존재하지_않는_작업의_새로운_작업을_진행하려는_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), 0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 다른_호스트의_작업의_새로운_작업을_진행하려는_경우_예외가_발생한다() {
        Host host1 = hostRepository.save(Host_생성("1234", 1234L));
        Host host2 = hostRepository.save(Host_생성("1234", 2345L));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        assertThatThrownBy(() -> taskService.createNewRunningTasks(host1.getId(), job.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 이미_진행중인_작업이_존재하는데_새로운_작업을_진행하려는_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task1 = Task_생성(section, "책상 청소");
        Task task2 = Task_생성(section, "의자 넣기");
        taskRepository.saveAll(List.of(task1, task2));
        runningTaskRepository.saveAll(
                List.of(RunningTask_생성(task1.getId(), true),
                        RunningTask_생성(task2.getId(), true)));

        assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), job.getId()))
                .isInstanceOf(BusinessException.class)
                .hasMessage("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.");
    }

    @Test
    void 정상적으로_새로운_진행_작업을_생성한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task1 = Task_생성(section, "책상 청소");
        Task task2 = Task_생성(section, "의자 넣기");
        taskRepository.saveAll(List.of(task1, task2));

        taskService.createNewRunningTasks(host.getId(), job.getId());
        List<RunningTask> result = runningTaskRepository.findAllById(Stream.of(task1, task2)
                .map(Task::getId)
                .collect(Collectors.toList()));

        assertThat(result).hasSize(2);
    }

    @Nested
    class 작업의_진행_여부는 {
        private Host host;
        private Space space;
        private Job job;
        private Section section;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            space = spaceRepository.save(Space_생성(host, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            section = sectionRepository.save(Section_생성(job, "트랙룸"));
            taskRepository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));
        }

        @Test
        void 존재하지_않는_호스트로_확인하려는_경우_예외가_발생한다() {
            assertThatThrownBy(() -> taskService.isJobActivated(0L, 1L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }

        @Test
        void 존재하지_않는_작업으로_확인하려는_경우_예외가_발생한다() {
            Host host = hostRepository.save(Host_생성("1234", 2345L));

            assertThatThrownBy(() -> taskService.isJobActivated(host.getId(), 0L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void 다른_호스트의_작업으로_확인하려는_경우_예외가_발생한다() {
            Host host1 = hostRepository.save(Host_생성("1234", 2345L));
            Space space = spaceRepository.save(Space_생성(host1, "잠실"));
            Job job = jobRepository.save(Job_생성(space, "청소"));

            assertThatThrownBy(() -> taskService.isJobActivated(host.getId(), job.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void 진행_작업이_존재하는_경우_참을_반환한다() {
            taskService.createNewRunningTasks(host.getId(), job.getId());

            JobActiveResponse result = taskService.isJobActivated(host.getId(), job.getId());

            assertThat(result.isActive()).isTrue();
        }

        @Test
        void 진행_작업이_존재하지_않는_경우_거짓을_반환한다() {
            JobActiveResponse result = taskService.isJobActivated(host.getId(), job.getId());

            assertThat(result.isActive()).isFalse();
        }
    }

    @Nested
    class 진행_작업_조회 {

        private Host host;
        private Space space;
        private Job job;
        private Section section;
        private Task task1, task2;

        @BeforeEach
        void init() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            space = spaceRepository.save(Space_생성(host, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            section = sectionRepository.save(Section_생성(job, "트랙룸"));
            task1 = Task_생성(section, "책상 청소");
            task2 = Task_생성(section, "의자 넣기");
        }

        @Test
        void 존재하지_않는_호스트로_진행중인_작업을_조회하려하는_경우_예외가_발생한다() {
            assertThatThrownBy(() -> taskService.findRunningTasks(0L, 1L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }

        @Test
        void 존재하지_않는_작업의_진행중인_작업을_조회하려는_경우_예외가_발생한다() {
            Host host = hostRepository.save(Host_생성("1234", 2345L));

            assertThatThrownBy(() -> taskService.findRunningTasks(host.getId(), 0L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void 다른_호스트의_작업의_진행중인_작업을_조회하려는_경우_예외가_발생한다() {
            Host differentHost = hostRepository.save(Host_생성("1234", 2345L));
            taskRepository.saveAll(List.of(task1, task2));
            RunningTask runningTask1 = RunningTask_생성(task1.getId(), false);
            RunningTask runningTask2 = RunningTask_생성(task2.getId(), false);
            runningTaskRepository.saveAll(List.of(runningTask1, runningTask2));
            entityManager.flush();
            entityManager.clear();

            assertThatThrownBy(() -> taskService.findRunningTasks(differentHost.getId(), job.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void 진행중인_작업이_없는데_진행중인_작업을_조회하려는_경우_예외가_발생한다() {
            taskRepository.saveAll(List.of(task1, task2));

            assertThatThrownBy(() -> taskService.findRunningTasks(host.getId(), job.getId()))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("현재 진행중인 작업이 존재하지 않아 조회할 수 없습니다");
        }

        @Test
        void 정상적으로_진행중인_작업을_조회한다() {
            taskRepository.saveAll(List.of(task1, task2));
            RunningTask runningTask1 = RunningTask_생성(task1.getId(), false);
            RunningTask runningTask2 = RunningTask_생성(task2.getId(), false);
            runningTaskRepository.saveAll(List.of(runningTask1, runningTask2));
            entityManager.flush();
            entityManager.clear();

            RunningTasksResponse result = taskService.findRunningTasks(host.getId(), job.getId());
            assertThat(result.getSections()).hasSize(1);
        }
    }

    @Test
    void 진행_작업_체크_시_진행_작업이_존재하지_않을_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = Task_생성(section, "책상 청소");
        taskRepository.save(task);

        assertThatThrownBy(() -> taskService.flipRunningTask(host.getId(), task.getId()))
                .isInstanceOf(BusinessException.class)
                .hasMessage("현재 진행 중인 작업이 아닙니다.");
    }

    @Test
    void 진행_작업_체크_시_입력한_host의_진행_작업이_아닐_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = Task_생성(section, "책상 청소");

        Host differentHost = hostRepository.save(Host_생성("1234", 2345L));
        Space differentSpace = spaceRepository.save(Space_생성(differentHost, "선릉"));
        Job differentJob = jobRepository.save(Job_생성(differentSpace, "청소"));
        Section differentSection = sectionRepository.save(Section_생성(differentJob, "트랙룸"));
        Task differentTask = Task_생성(differentSection, "책상 청소");

        taskRepository.save(task);
        taskRepository.save(differentTask);
        runningTaskRepository.save(RunningTask_생성(task.getId(), false));
        runningTaskRepository.save(RunningTask_생성(differentTask.getId(), false));

        assertThatThrownBy(() -> taskService.flipRunningTask(differentHost.getId(), task.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 진행_작업_체크_시_Host가_존재하지_않는_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = Task_생성(section, "책상 청소");
        taskRepository.save(task);
        runningTaskRepository.save(RunningTask_생성(task.getId(), false));

        assertThatThrownBy(() -> taskService.flipRunningTask(0L, task.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"false:true", "true:false"}, delimiter = ':')
    void 진행_작업의_상태를_변경한다(final boolean input, final boolean expected) {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = Task_생성(section, "책상 청소");
        taskRepository.save(task);
        runningTaskRepository.save(RunningTask_생성(task.getId(), input));

        taskService.flipRunningTask(host.getId(), task.getId());

        RunningTask runningTask = runningTaskRepository.findByTaskId(task.getId()).get();
        assertThat(runningTask.isChecked()).isEqualTo(expected);
    }
}
