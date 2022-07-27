package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.core.application.response.JobsResponse;
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
import com.woowacourse.gongcheck.core.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.core.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.core.presentation.request.SlackUrlChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.TaskCreateRequest;
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
@DisplayName("JobService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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

    @Nested
    class findJobs_메소드는 {

        @Nested
        class Job_목록이_세개가_있는_경우 {

            private Host host;
            private Space space;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job_1 = Job_생성(space, "오픈");
                Job job_2 = Job_생성(space, "청소");
                Job job_3 = Job_생성(space, "마감");
                jobRepository.saveAll(List.of(job_1, job_2, job_3));
            }

            @Test
            void 세개가_포함된_Job_목록을_조회한다() {
                JobsResponse result = jobService.findJobs(host.getId(), space.getId());

                assertThat(result.getJobs()).hasSize(3);
            }
        }

        @Nested
        class 존재하지_않는_Host로_조회할_경우 {

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(0L, 1L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Space의_Job_목록을_조회할_경우 {

            private Long hostId;

            @BeforeEach
            void setUp() {
                hostId = hostRepository.save(Host_생성("1234", 1234L))
                        .getId();
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(hostId, 0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 공간입니다.");
            }
        }

        @Nested
        class 다른_Host의_Space의_Job_목록을_조회할_경우 {

            private Host otherHost;
            private Space space;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                otherHost = hostRepository.save(Host_생성("1234", 2345L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(otherHost.getId(), space.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 공간입니다.");
            }
        }
    }

    @Nested
    class createJob_메소드는 {

        @Nested
        class Job과_Section들과_Task들을_입력_받는_경우 {

            private Host host;
            private Space space;
            private JobCreateRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
                List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 한_번에_생성한다() {
                Long savedJobId = jobService.createJob(host.getId(), space.getId(), request);

                assertThat(savedJobId).isNotNull();
            }
        }

        @Nested
        class Host가_존재하지_않는데_Job을_생성하려는_경우 {

            private Space space;
            private JobCreateRequest request;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
                List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.createJob(0L, space.getId(), request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class Space가_존재하지_않는데_Job을_생성하려는_경우 {

            private Host host;
            private JobCreateRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
                List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.createJob(host.getId(), 0L, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 공간입니다.");
            }
        }
    }


    @Nested
    class updateJob_메소드는 {

        @Nested
        class 기존의_Job이_존재하는_경우 {

            private Host host;
            private JobCreateRequest request;
            private Long savedJobId;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
                List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
                request = new JobCreateRequest("청소", sections);
                savedJobId = jobService.createJob(host.getId(), space.getId(), request);
            }

            @Test
            void 기존의_존재하는_Job을_삭제한_후_새로운_Job을_생성하여_수정한다() {
                Long updateJobId = jobService.updateJob(host.getId(), savedJobId, request);

                assertThat(updateJobId).isNotNull();
            }
        }

        @Nested
        class host가_존재하지_않는_경우 {

            private JobCreateRequest request;
            private Long savedJobId;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
                List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
                request = new JobCreateRequest("청소", sections);
                savedJobId = jobService.createJob(host.getId(), space.getId(), request);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.updateJob(0L, savedJobId, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class host에_해당하는_jobId가_아닐_경우 {

            private JobCreateRequest request;
            private Long savedJobId;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
                List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
                request = new JobCreateRequest("청소", sections);
                savedJobId = jobService.createJob(host.getId(), space.getId(), request);
            }

            @Test
            void 예외가_발생한다() {
                Host anotherHost = hostRepository.save(Host_생성("1234", 2345L));
                assertThatThrownBy(() -> jobService.updateJob(anotherHost.getId(), savedJobId, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job을_수정할_경우 {

            private Host host;
            private JobCreateRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
                List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.updateJob(host.getId(), 0L, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class removeJob_메소드는 {

        @Nested
        class Host가_존재하지_않는_경우 {

            private Job job;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.removeJob(0L, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class Job이_존재하지_않는_경우 {

            private Host host;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.removeJob(host.getId(), 0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Job_제거할_경우 {

            private Job job;
            private Host otherHost;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                otherHost = hostRepository.save(Host_생성("1234", 2345L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.removeJob(otherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 존재하는_Job이_있는_경우 {

            private Host host;
            private Job job;
            private Section section;
            private Task task;
            private RunningTask runningTask;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실 캠퍼스"));
                job = jobRepository.save(Job_생성(space, "청소"));
                section = sectionRepository.save(Section_생성(job, "대강의실"));
                task = taskRepository.save(Task_생성(section, "책상 닦기"));
                runningTask = runningTaskRepository.save(RunningTask_생성(task.getId(), false));
            }

            @Test
            void Job과_관련된_Section_Task_RunningTask를_함께_삭제한다() {
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
        }

        @Nested
        class findSlackUrl_메소드는 {

            @Nested
            class Host가_존재하지_않는_경우 {

                @Test
                void 예외가_발생한다() {
                    assertThatThrownBy(() -> jobService.findSlackUrl(0L, 0L))
                            .isInstanceOf(NotFoundException.class)
                            .hasMessage("존재하지 않는 호스트입니다.");
                }
            }

            @Nested
            class Job이_존재하지_않는_경우 {

                private Host host;

                @BeforeEach
                void setUp() {
                    host = hostRepository.save(Host_생성("1234", 1234L));
                }

                @Test
                void 예외가_발생한다() {
                    assertThatThrownBy(() -> jobService.findSlackUrl(host.getId(), 0L))
                            .isInstanceOf(NotFoundException.class)
                            .hasMessage("존재하지 않는 작업입니다.");
                }
            }
        }

        @Nested
        class Job이_존재하는데_다른_Host의_Job의_Slack_Url_조회하는_경우 {

            private Host myHost;
            private Job otherJob;

            @BeforeEach
            void setUp() {
                myHost = hostRepository.save(Host_생성("1234", 1234L));
                Host otherHost = hostRepository.save(Host_생성("1234", 2456L));
                Space otherSpace = spaceRepository.save(Space_생성(otherHost, "잠실"));
                otherJob = jobRepository.save(Job_생성(otherSpace, "톱오브스윙방"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.findSlackUrl(myHost.getId(), otherJob.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class Job이_존재하는_경우 {

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "톱오브스윙방", "http://slackurl.com"));
            }

            @Test
            void Slack_Url을_조회한다() {
                assertThat(jobService.findSlackUrl(host.getId(), job.getId()).getSlackUrl()).isEqualTo(
                        "http://slackurl.com");
            }
        }
    }


    @Nested
    class changeSlackUrl_메소드는 {

        @Nested
        class 존재하지_않는_Host를_입력_받는_경우 {

            private SlackUrlChangeRequest request;

            @BeforeEach
            void setUp() {
                request = new SlackUrlChangeRequest("https://newslackurl.com");
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.changeSlackUrl(0L, 0L, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job을_입력_받는_경우 {

            private SlackUrlChangeRequest request;
            private Host host;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                request = new SlackUrlChangeRequest("https://newslackurl.com");
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.changeSlackUrl(host.getId(), 0L, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_JobId를_입력_받는_경우 {

            private Host host;
            private Job otherJob;
            private SlackUrlChangeRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Host otherHost = hostRepository.save(Host_생성("1234", 2456L));
                Space otherSpace = spaceRepository.save(Space_생성(otherHost, "잠실"));
                otherJob = jobRepository.save(Job_생성(otherSpace, "톱오브스윙방"));
                request = new SlackUrlChangeRequest("https://newslackurl.com");
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.changeSlackUrl(host.getId(), otherJob.getId(), request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }
        @Nested
        class 존재하는_Job을_입력_받는_경우 {

            private Host host;
            private Job job;
            private SlackUrlChangeRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "톱오브스윙방", "http://slackurl.com"));
                request = new SlackUrlChangeRequest("https://newslackurl.com");
            }

            @Test
            void Slack_Url을_수정한다() {
                jobService.changeSlackUrl(host.getId(), job.getId(), request);

                assertThat(job.getSlackUrl()).isEqualTo("https://newslackurl.com");
            }
        }
    }
}
