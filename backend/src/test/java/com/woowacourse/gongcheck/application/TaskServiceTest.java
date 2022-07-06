package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import org.junit.jupiter.api.Test;
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

    @Test
    void 존재하지_않는_호스트로_새로운_작업을_진행하려하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> taskService.createNewRunningTasks(0L, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 존재하지_않는_작업의_새로운_작업을_진행하려는_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234"));

        assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), 0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 다른_호스트의_작업의_새로운_작업을_진행하려는_경우_예외가_발생한다() {
        Host host1 = hostRepository.save(Host_생성("1234"));
        Host host2 = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        assertThatThrownBy(() -> taskService.createNewRunningTasks(host1.getId(), job.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 이미_진행중인_작업이_존재하는데_새로운_작업을_진행하려는_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task1 = Task_생성(section, "책상 청소");
        Task task2 = Task_생성(section, "의자 넣기");
        taskRepository.saveAll(List.of(task1, task2));
        runningTaskRepository.saveAll(List.of(RunningTask_생성(task1), RunningTask_생성(task2)));

        assertThatThrownBy(() -> taskService.createNewRunningTasks(host.getId(), job.getId()))
                .isInstanceOf(BusinessException.class)
                .hasMessage("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.");
    }

    @Test
    void 정상적으로_새로운_진행_작업을_생성한다() {
        Host host = hostRepository.save(Host_생성("1234"));
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
}
