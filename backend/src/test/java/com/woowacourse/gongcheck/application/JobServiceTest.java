package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.application.response.JobsResponse;
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
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SlackUrlChangeRequest;
import com.woowacourse.gongcheck.presentation.request.TaskCreateRequest;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JobServiceTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JobService jobService;

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
    void Job_목록을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job1 = Job_생성(space, "오픈");
        Job job2 = Job_생성(space, "청소");
        Job job3 = Job_생성(space, "마감");
        jobRepository.saveAll(List.of(job1, job2, job3));

        JobsResponse result = jobService.findPage(host.getId(), space.getId(), PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getJobs()).hasSize(2),
                () -> assertThat(result.isHasNext()).isTrue()
        );
    }

    @Test
    void 존재하지_않는_Host로_Job_목록을_조회할_경우_예외를_던진다() {
        assertThatThrownBy(() -> jobService.findPage(0L, 1L, PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 존재하지_않는_Space의_Job_목록을_조회할_경우_예외를_던진다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        assertThatThrownBy(() -> jobService.findPage(host.getId(), 0L, PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }

    @Test
    void 다른_Host의_Space의_Job_목록을_조회할_경우_예외를_던진다() {
        Host host1 = hostRepository.save(Host_생성("1234", 1234L));
        Host host2 = hostRepository.save(Host_생성("1234", 2345L));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));

        assertThatThrownBy(() -> jobService.findPage(host1.getId(), space.getId(), PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }

    @Test
    void Job과_Section들과_Task들을_한_번에_생성한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest jobCreateRequest = new JobCreateRequest("청소", sections);

        Long savedJobId = jobService.createJob(host.getId(), space.getId(), jobCreateRequest);

        assertThat(savedJobId).isNotNull();
    }

    @Test
    void Host가_존재하지_않는데_Job_생성_시_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest jobCreateRequest = new JobCreateRequest("청소", sections);

        assertThatThrownBy(() -> jobService.createJob(0L, space.getId(), jobCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void Space가_존재하지_않는데_Job_생성_시_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest jobCreateRequest = new JobCreateRequest("청소", sections);

        assertThatThrownBy(() -> jobService.createJob(host.getId(), 0L, jobCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }

    @Nested
    class Job을_수정한다 {
        Host host;
        Space space;
        List<TaskCreateRequest> tasks;
        List<SectionCreateRequest> sections;
        JobCreateRequest jobCreateRequest;
        Long savedJobId;

        @BeforeEach
        void init() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            space = spaceRepository.save(Space_생성(host, "잠실"));
            tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
            sections = List.of(new SectionCreateRequest("대강의실", tasks));
            jobCreateRequest = new JobCreateRequest("청소", sections);
            savedJobId = jobService.createJob(host.getId(), space.getId(), jobCreateRequest);
        }

        @Test
        void 기존의_존재하는_Job을_삭제한_후_새로운_Job을_생성하여_수정한다() {
            Long updateJobId = jobService.updateJob(host.getId(), savedJobId, jobCreateRequest);

            assertThat(updateJobId).isNotNull();
        }

        @Test
        void host가_존재하지_않을_경우_예외가_발생한다() {
            assertThatThrownBy(() -> jobService.updateJob(0L, savedJobId, jobCreateRequest))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }

        @Test
        void host에_해당하는_jobId가_아닐_경우_예외가_발생한다() {
            assertThatThrownBy(() -> jobService.updateJob(1L, savedJobId, jobCreateRequest))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void 존재하지_않는_Job을_수정할_경우_예외가_발생한다() {
            assertThatThrownBy(() -> jobService.updateJob(host.getId(), 0L, jobCreateRequest))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }
    }

    @Test
    void Host가_존재하지_않는데_Job_제거_시_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        assertThatThrownBy(() -> jobService.removeJob(0L, job.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void Job이_존재하지_않는데_Job_제거_시_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));

        assertThatThrownBy(() -> jobService.removeJob(host.getId(), 0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 다른_Host의_Job_제거_시_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Host anotherHost = hostRepository.save(Host_생성("1234", 2345L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        assertThatThrownBy(() -> jobService.removeJob(anotherHost.getId(), job.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void Job을_삭제하면_관련된_Section_Task_RunningTask를_함께_삭제한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실 캠퍼스"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "대강의실"));
        Task task = taskRepository.save(Task_생성(section, "책상 닦기"));
        RunningTask runningTask = runningTaskRepository.save(RunningTask_생성(task.getId(), false));

        jobService.removeJob(host.getId(), job.getId());

        entityManager.flush();
        entityManager.clear();
        assertAll(
                () -> assertThat(jobRepository.findById(job.getId())).isEmpty(),
                () -> assertThat(sectionRepository.findById(section.getId())).isEmpty(),
                () -> assertThat(taskRepository.findById(task.getId())).isEmpty(),
                () -> assertThat(runningTaskRepository.findById(runningTask.getTaskId())).isEmpty()
        );
    }

    @Test
    void Host가_존재하지_않는데_Slack_Url_조회_시_예외가_발생한다() {
        assertThatThrownBy(() -> jobService.findSlackUrl(0L, 0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void Job이_존재하지_않는데_Slack_Url_조회_시_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        assertThatThrownBy(() -> jobService.findSlackUrl(host.getId(), 0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void Job이_존재하는데_다른_Host의_Job의_Slack_Url_조회_시_예외가_발생한다() {
        Host myHost = hostRepository.save(Host_생성("1234", 1234L));
        Host otherHost = hostRepository.save(Host_생성("1234", 2456L));
        Space otherSpace = spaceRepository.save(Space_생성(otherHost, "잠실"));
        Job otherJob = jobRepository.save(Job_생성(otherSpace, "톱오브스윙방"));

        assertThatThrownBy(() -> jobService.findSlackUrl(myHost.getId(), otherJob.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void Job의_Slack_Url을_정상적으로_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "톱오브스윙방", "http://slackurl.com"));

        assertThat(jobService.findSlackUrl(host.getId(), job.getId()).getSlackUrl()).isEqualTo("http://slackurl.com");
    }

    @Nested
    class Job_Slack_Url_수정_시 {

        private Host host;
        private SlackUrlChangeRequest request;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            request = new SlackUrlChangeRequest("https://newslackurl.com");
        }

        @Test
        void Host가_존재하지_않으면_예외가_발생한다() {
            assertThatThrownBy(() -> jobService.changeSlackUrl(0L, 0L, request))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }

        @Test
        void Job이_존재하지_않으면_예외가_발생한다() {
            assertThatThrownBy(() -> jobService.changeSlackUrl(host.getId(), 0L, request))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void 다른_Host의_것을_수정할_시_예외가_발생한다() {
            Host otherHost = hostRepository.save(Host_생성("1234", 2456L));
            Space otherSpace = spaceRepository.save(Space_생성(otherHost, "잠실"));
            Job otherJob = jobRepository.save(Job_생성(otherSpace, "톱오브스윙방"));

            assertThatThrownBy(() -> jobService.changeSlackUrl(host.getId(), otherJob.getId(), request))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 작업입니다.");
        }

        @Test
        void 정상적으로_수정한다() {
            Space space = spaceRepository.save(Space_생성(host, "잠실"));
            Job job = jobRepository.save(Job_생성(space, "톱오브스윙방", "http://slackurl.com"));

            jobService.changeSlackUrl(host.getId(), job.getId(), request);

            assertThat(job.getSlackUrl()).isEqualTo("https://newslackurl.com");
        }
    }
}
