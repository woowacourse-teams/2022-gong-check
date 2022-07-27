package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Submission_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.core.application.response.SubmissionCreatedResponse;
import com.woowacourse.gongcheck.core.application.response.SubmissionsResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.section.SectionRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.core.domain.submission.SubmissionRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.TaskRepository;
import com.woowacourse.gongcheck.core.presentation.request.SubmissionRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
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

    @Nested
    class Submission_생성_시 {

        private final SubmissionRequest request = new SubmissionRequest("제출자");

        private Host hostWithoutTasks;
        private Host hostWithTasks;
        private Space space;
        private Job job;
        private Section section;
        private Task task1;
        private Task task2;

        @BeforeEach
        void setUp() {
            hostWithoutTasks = hostRepository.save(Host_생성("1234", 1234L));
            hostWithTasks = hostRepository.save(Host_생성("1234", 2345L));
            space = spaceRepository.save(Space_생성(hostWithTasks, "잠실"));
            job = jobRepository.save(Job_생성(space, "청소"));
            section = sectionRepository.save(Section_생성(job, "트랙룸"));
            task1 = Task_생성(section, "책상 청소");
            task2 = Task_생성(section, "의자 넣기");
            taskRepository.saveAll(List.of(task1, task2));
        }

        @Test
        void Host가_존재하지_않는_경우_예외가_발생한다() {
            assertThatThrownBy(() -> submissionService.submitJobCompletion(0L, 1L, request))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }

        @Test
        void Job이_존재하지_않는_경우_예외가_발생한다() {
            assertThatThrownBy(() -> submissionService.submitJobCompletion(hostWithTasks.getId(), 0L, request))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void 다른_Host의_Job으로_Submission을_생성하려는_경우_예외가_발생한다() {
            assertThatThrownBy(
                    () -> submissionService.submitJobCompletion(hostWithoutTasks.getId(), job.getId(), request))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void RunningTask가_존재하지_않는_경우_Submission을_생성할_때_예외가_발생한다() {
            assertThatThrownBy(() -> submissionService.submitJobCompletion(hostWithTasks.getId(), job.getId(), request))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("현재 제출할 수 있는 진행중인 작업이 존재하지 않습니다.");
        }

        @Test
        void 모든_RunningTask가_체크상태가_아니면_Submission을_생성할_때_예외가_발생한다() {
            checkAllRunningTasks(false);

            assertThatThrownBy(() -> submissionService.submitJobCompletion(hostWithTasks.getId(), job.getId(), request))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("모든 작업이 완료되지않아 제출이 불가합니다.");
        }

        @Test
        void Submission을_생성한다() {
            checkAllRunningTasks(true);

            SubmissionCreatedResponse submissionCreatedResponse = submissionService.submitJobCompletion(hostWithTasks.getId(),
                    job.getId(), request);

            assertAll(
                    () -> assertThat(submissionRepository.findAll().size()).isOne(),
                    () -> assertThat(runningTaskRepository.findAll().size()).isZero(),
                    () -> assertThat(submissionCreatedResponse.getAuthor()).isEqualTo(request.getAuthor()),
                    () -> assertThat(submissionCreatedResponse.getSpaceName()).isEqualTo(space.getName().getValue()),
                    () -> assertThat(submissionCreatedResponse.getJobName()).isEqualTo(job.getName())
            );
        }

        private void checkAllRunningTasks(final boolean isChecked) {
            RunningTask runningTask1 = RunningTask_생성(task1.getId(), true);
            RunningTask runningTask2 = RunningTask_생성(task2.getId(), isChecked);
            runningTaskRepository.saveAll(List.of(runningTask1, runningTask2));
        }
    }

    @Nested
    class Submission_목록을_응답한다 {
        private Host host;
        private Host anotherHost;
        private Space space;
        private Job job1;
        private Job job2;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1111L));
            anotherHost = hostRepository.save(Host_생성("1234", 2222L));
            space = spaceRepository.save(Space_생성(host, "잠실"));
            job1 = jobRepository.save(Job_생성(space, "오픈"));
            job2 = jobRepository.save(Job_생성(space, "마감"));
            submissionRepository.save(Submission_생성(job1));
            submissionRepository.save(Submission_생성(job2));
            submissionRepository.save(Submission_생성(job2));
        }

        @Test
        void 존재하지_않는_Host에_해당하는_Submission을_조회하려는_경우_예외를_발생시킨다() {
            Long spaceId = space.getId();
            PageRequest pageRequest = PageRequest.of(0, 2);
            assertThatThrownBy(() -> submissionService.findPage(0L, spaceId, pageRequest))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }

        @Test
        void 존재하지_않는_Space에_해당하는_Submission을_조회하려는_경우_예외를_발생시킨다() {
            Long hostId = host.getId();
            PageRequest pageRequest = PageRequest.of(0, 2);
            assertThatThrownBy(() -> submissionService.findPage(hostId, 0L, pageRequest))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 공간입니다.");
        }

        @Test
        void 입력된_SpaceId가_입력된_Host의_Space가_아닌_경우_예외를_발생시킨다() {
            Long anotherHostId = anotherHost.getId();
            Long spaceId = space.getId();
            PageRequest pageRequest = PageRequest.of(0, 2);
            assertThatThrownBy(() -> submissionService.findPage(anotherHostId, spaceId, pageRequest))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 공간입니다.");
        }

        @Test
        void 입력받은_Space에_해당하는_Submission_응답을_반환한다() {
            SubmissionsResponse response = submissionService.findPage(host.getId(), space.getId(),
                    PageRequest.of(0, 2));

            assertAll(
                    () -> assertThat(response.getSubmissions()).hasSize(2),
                    () -> assertThat(response.isHasNext()).isTrue()
            );
        }
    }
}