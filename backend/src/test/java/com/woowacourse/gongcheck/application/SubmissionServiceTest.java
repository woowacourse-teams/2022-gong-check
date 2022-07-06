package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.section.SectionRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.domain.submission.SubmissionRepository;
import com.woowacourse.gongcheck.domain.task.RunningTask;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.domain.task.Task;
import com.woowacourse.gongcheck.domain.task.TaskRepository;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SubmissionServiceTest {

    @Autowired
    private SubmissionService submissionService;

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
    private SubmissionRepository submissionRepository;

    @Test
    void 존재하지_않는_호스트로_제출을_시도할_경우_예외가_발생한다() {
        SubmissionRequest request = new SubmissionRequest("제출자");

        assertThatThrownBy(() -> submissionService.submitJobCompletion(0L, 1L, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 존재하지_않는_작업으로_제출을_시도할_경우_예외가_발생한다() {
        SubmissionRequest request = new SubmissionRequest("제출자");
        Host host = hostRepository.save(Host_생성("1234"));

        assertThatThrownBy(() -> submissionService.submitJobCompletion(host.getId(), 0L, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 다른_호스트의_작업으로_제출을_시도할_경우_예외가_발생한다() {
        SubmissionRequest request = new SubmissionRequest("제출자");
        Host host1 = hostRepository.save(Host_생성("1234"));
        Host host2 = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        assertThatThrownBy(() -> submissionService.submitJobCompletion(host1.getId(), job.getId(), request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 현재_진행중인_작업이_없는데_제출을_시도할_경우_예외가_발생한다() {
        SubmissionRequest request = new SubmissionRequest("제출자");
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        taskRepository.saveAll(List.of(Task_생성(section, "책상 청소"), Task_생성(section, "의자 넣기")));

        assertThatThrownBy(() -> submissionService.submitJobCompletion(host.getId(), job.getId(), request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("현재 제출할 수 있는 진행중인 작업이 존재하지 않습니다.");
    }

    @Test
    void 현재_진행중인_작업을_미완료_상태로_제출을_시도할_경우_예외가_발생한다() {
        SubmissionRequest request = new SubmissionRequest("제출자");
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task1 = Task_생성(section, "책상 청소");
        Task task2 = Task_생성(section, "의자 넣기");
        taskRepository.saveAll(List.of(task1, task2));
        RunningTask runningTask1 = RunningTask.builder()
                .taskId(task1.getId())
                .isChecked(true)
                .createdAt(LocalDateTime.now())
                .build();
        RunningTask runningTask2 = RunningTask.builder()
                .taskId(task1.getId())
                .isChecked(false)
                .createdAt(LocalDateTime.now())
                .build();
        runningTaskRepository.saveAll(List.of(runningTask1, runningTask2));

        assertThatThrownBy(() -> submissionService.submitJobCompletion(host.getId(), job.getId(), request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("모든 작업이 완료되지않아 제출이 불가합니다.");
    }

    @Test
    void 현재_진행중인_작업이_모두_완료된_상태로_제출한다() {
        SubmissionRequest request = new SubmissionRequest("제출자");
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task1 = Task_생성(section, "책상 청소");
        Task task2 = Task_생성(section, "의자 넣기");
        taskRepository.saveAll(List.of(task1, task2));
        RunningTask runningTask1 = RunningTask.builder()
                .taskId(task1.getId())
                .isChecked(true)
                .createdAt(LocalDateTime.now())
                .build();
        RunningTask runningTask2 = RunningTask.builder()
                .taskId(task1.getId())
                .isChecked(true)
                .createdAt(LocalDateTime.now())
                .build();
        runningTaskRepository.saveAll(List.of(runningTask1, runningTask2));

        submissionService.submitJobCompletion(host.getId(), job.getId(), request);
        assertAll(
                () -> assertThat(submissionRepository.findAll().size()).isEqualTo(1),
                () -> assertThat(runningTaskRepository.findAll().size()).isEqualTo(0)
        );
    }
}
