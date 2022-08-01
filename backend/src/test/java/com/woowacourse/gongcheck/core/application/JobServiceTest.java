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

import com.woowacourse.gongcheck.core.application.response.JobResponse;
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
import java.util.stream.Collectors;
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
        class Job_목록이_존재하는_경우 {

            private Host host;
            private Space space;
            private List<String> jobNames;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
                List<Job> jobs = jobRepository.saveAll(
                        List.of(Job_생성(space, "오픈"), Job_생성(space, "청소"), Job_생성(space, "마감")));
                jobNames = jobs.stream()
                        .map(Job::getName)
                        .collect(Collectors.toList());
            }

            @Test
            void Job_목록을_조회한다() {
                List<JobResponse> result = jobService.findJobs(host.getId(), space.getId()).getJobs();

                assertAll(
                        () -> assertThat(result)
                                .extracting(JobResponse::getName)
                                .containsAll(jobNames),
                        () -> assertThat(result).hasSize(jobNames.size())
                );
            }
        }

        @Nested
        class 존재하지_않는_Host로_조회할_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private Host host;
            private Space space;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(NON_EXIST_HOST_ID, space.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Space의_Job_목록을_조회할_경우 {

            private static final long NON_EXIST_SPACE_ID = 0L;

            private long hostId;

            @BeforeEach
            void setUp() {
                hostId = hostRepository.save(Host_생성("1234", 1234L))
                        .getId();
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(hostId, NON_EXIST_SPACE_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 공간입니다.");
            }
        }

        @Nested
        class 다른_Host의_Space의_Job_목록을_조회할_경우 {

            private Host host;
            private Space space;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Host otherHost = hostRepository.save(Host_생성("1234", 2345L));
                space = spaceRepository.save(Space_생성(otherHost, "잠실"));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(host.getId(), space.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 공간입니다.");
            }
        }
    }

    @Nested
    class createJob_메소드는 {

        @Nested
        class Job_Section들과_Task들을_입력_받는_경우 {

            private Host host;
            private Space space;
            private JobCreateRequest request;
            private List<String> taskCreateRequestNames;
            private List<String> sectionCreateRequestNames;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List
                        .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                                new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
                List<SectionCreateRequest> sections = List.of(
                        new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks));
                sectionCreateRequestNames = sections.stream()
                        .map(SectionCreateRequest::getName)
                        .collect(Collectors.toList());
                taskCreateRequestNames = tasks.stream()
                        .map(TaskCreateRequest::getName)
                        .collect(Collectors.toList());
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 한_번에_생성한다() {
                long savedJobId = jobService.createJob(host.getId(), space.getId(), request);

                Job savedJob = jobRepository.getById(savedJobId);
                List<Section> savedSections = sectionRepository.findAllByJob(savedJob);
                List<Task> savedTasks = taskRepository.findAllBySectionIn(savedSections);

                assertAll(
                        () -> assertThat(savedJob.getId()).isEqualTo(savedJobId),
                        () -> assertThat(savedTasks)
                                .extracting(Task::getName)
                                .containsAll(taskCreateRequestNames),
                        () -> assertThat(savedSections)
                                .extracting(Section::getName)
                                .containsAll(sectionCreateRequestNames)
                );
            }
        }

        @Nested
        class 존재하지_않는_Host의_id를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private Space space;
            private JobCreateRequest request;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List
                        .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                                new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
                List<SectionCreateRequest> sections = List.of(
                        new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks));
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.createJob(NON_EXIST_HOST_ID, space.getId(), request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Space의_id를_입력받은_경우 {

            private static final long NON_EXIST_SPACE_ID = 0L;

            private Host host;
            private JobCreateRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                List<TaskCreateRequest> tasks = List
                        .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                                new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
                List<SectionCreateRequest> sections = List.of(
                        new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks));
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.createJob(host.getId(), NON_EXIST_SPACE_ID, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 공간입니다.");
            }
        }

        @Nested
        class 다른_host의_space_id를_입력받은_경우 {

            private Host host;
            private Space otherSpace;
            private JobCreateRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                spaceRepository.save(Space_생성(host, "잠실"));
                Host otherHost = hostRepository.save(Host_생성("5678", 5678L));
                otherSpace = spaceRepository.save(Space_생성(otherHost, "잠실"));
                List<TaskCreateRequest> tasks = List
                        .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                                new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
                List<SectionCreateRequest> sections = List.of(
                        new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks));
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.createJob(host.getId(), otherSpace.getId(), request))
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
            private Job originJob;
            private Section originSection;
            private Task originTask;
            private JobCreateRequest request;
            private List<String> requestSectionNames;
            List<String> requestTaskNames;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                originJob = jobRepository.save(Job_생성(space, "마감"));
                originSection = sectionRepository.save(Section_생성(originJob, "소강의실"));
                originTask = taskRepository.save(Task_생성(originSection, "불 끄기"));

                List<TaskCreateRequest> tasks = List
                        .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                                new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
                List<SectionCreateRequest> sections = List.of(
                        new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks));
                request = new JobCreateRequest("청소", sections);
                List<SectionCreateRequest> requestSections = request.getSections();
                requestSectionNames = requestSections.stream()
                        .map(SectionCreateRequest::getName)
                        .collect(Collectors.toList());
                requestTaskNames = requestSections.get(0)
                        .getTasks()
                        .stream()
                        .map(TaskCreateRequest::getName)
                        .collect(Collectors.toList());
            }

            @Test
            void 기존에_존재하던_Job을_삭제한_후_새로운_Job을_생성한다() {
                long updateJobId = jobService.updateJob(host.getId(), originJob.getId(), request);

                Job updateJob = jobRepository.findBySpaceHostAndId(host, updateJobId)
                        .get();
                List<Section> updateSections = sectionRepository.findAllByJob(updateJob);
                List<Task> updateTasks = taskRepository.findAllBySectionIn(updateSections);

                assertAll(
                        () -> assertThat(updateJob.getName()).isEqualTo(request.getName()),
                        () -> assertThat(updateSections).doesNotContain(originSection),
                        () -> assertThat(updateTasks).doesNotContain(originTask),
                        () -> assertThat(updateSections)
                                .extracting(Section::getName)
                                .containsAll(requestSectionNames),
                        () -> assertThat(updateTasks)
                                .extracting(Task::getName)
                                .containsAll(requestTaskNames)
                );
            }
        }

        @Nested
        class 존재하지_않는_Host의_id를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private JobCreateRequest request;
            private long savedJobId;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List
                        .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                                new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
                List<SectionCreateRequest> sections = List.of(
                        new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks));
                request = new JobCreateRequest("청소", sections);
                savedJobId = jobService.createJob(host.getId(), space.getId(), request);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.updateJob(NON_EXIST_HOST_ID, savedJobId, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 다른_host의_job_id를_입력받은_경우 {

            private JobCreateRequest request;
            private long savedJobId;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                List<TaskCreateRequest> tasks = List
                        .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                                new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
                List<SectionCreateRequest> sections = List.of(
                        new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks));
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
        class 존재하지_않는_job_id를_입력받을_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private Host host;
            private JobCreateRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                List<TaskCreateRequest> tasks = List
                        .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                                new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
                List<SectionCreateRequest> sections = List.of(
                        new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks));
                request = new JobCreateRequest("청소", sections);
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.updateJob(host.getId(), NON_EXIST_JOB_ID, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class removeJob_메소드는 {

        @Nested
        class 존재하지_않는_Host의_id를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private Job job;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.removeJob(NON_EXIST_HOST_ID, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class Job이_존재하지_않는_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private Host host;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.removeJob(host.getId(), NON_EXIST_JOB_ID))
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
            class 존재하지_않는_Host의_id를_입력받은_경우 {

                private static final long NON_EXIST_HOST_ID = 0L;
                private static final long NON_EXIST_JOB_ID = 0L;

                @Test
                void 예외가_발생한다() {
                    assertThatThrownBy(() -> jobService.findSlackUrl(NON_EXIST_HOST_ID, NON_EXIST_JOB_ID))
                            .isInstanceOf(NotFoundException.class)
                            .hasMessage("존재하지 않는 호스트입니다.");
                }
            }

            @Nested
            class Job이_존재하지_않는_경우 {

                private static final long NON_EXIST_JOB_ID = 0L;

                private Host host;

                @BeforeEach
                void setUp() {
                    host = hostRepository.save(Host_생성("1234", 1234L));
                }

                @Test
                void 예외가_발생한다() {
                    assertThatThrownBy(() -> jobService.findSlackUrl(host.getId(), NON_EXIST_JOB_ID))
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

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            private SlackUrlChangeRequest request;

            @BeforeEach
            void setUp() {
                request = new SlackUrlChangeRequest("https://newslackurl.com");
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.changeSlackUrl(NON_EXIST_HOST_ID, JOB_ID, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job을_입력_받는_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private SlackUrlChangeRequest request;
            private Host host;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                request = new SlackUrlChangeRequest("https://newslackurl.com");
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.changeSlackUrl(host.getId(), NON_EXIST_JOB_ID, request))
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

            private static final String SLACK_URL = "http://slackurl.com";
            private static final String NEW_SLACK_URL = "https://newslackurl.com";

            private Host host;
            private Job job;
            private SlackUrlChangeRequest request;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "톱오브스윙방", SLACK_URL));
                request = new SlackUrlChangeRequest(NEW_SLACK_URL);
            }

            @Test
            void Slack_Url을_수정한다() {
                jobService.changeSlackUrl(host.getId(), job.getId(), request);

                assertThat(job.getSlackUrl()).isEqualTo(NEW_SLACK_URL);
            }
        }
    }
}
