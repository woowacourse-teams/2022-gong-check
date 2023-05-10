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

import com.woowacourse.gongcheck.ApplicationTest;
import com.woowacourse.gongcheck.SupportRepository;
import com.woowacourse.gongcheck.core.application.response.JobResponse;
import com.woowacourse.gongcheck.core.application.response.SlackUrlResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.section.SectionRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.TaskRepository;
import com.woowacourse.gongcheck.core.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.core.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.core.presentation.request.SlackUrlChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.TaskCreateRequest;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("JobService 클래스의")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JobServiceTest {

    @Autowired
    private JobService jobService;

    @Autowired
    private SupportRepository repository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Nested
    class createJob_메소드는 {

        @Nested
        class Job_Section들과_Task들을_입력_받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final List<TaskCreateRequest> taskRequests = List.of(
                    new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                    new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            private final List<SectionCreateRequest> sectionRequests = List.of(
                    new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123",
                            taskRequests));
            private final JobCreateRequest request = new JobCreateRequest("청소", sectionRequests);

            @Test
            void 한_번에_생성한다() {
                long savedJobId = jobService.createJob(host.getId(), space.getId(), request);

                Job savedJob = repository.getById(Job.class, savedJobId);
                List<Section> savedSections = sectionRepository.findAllByJob(savedJob);
                List<Task> savedTasks = taskRepository.findAllBySectionIn(savedSections);

                List<String> sectionCreateRequestNames = sectionRequests.stream()
                        .map(SectionCreateRequest::getName)
                        .collect(Collectors.toList());
                List<String> taskCreateRequestNames = taskRequests.stream()
                        .map(TaskCreateRequest::getName)
                        .collect(Collectors.toList());

                assertAll(
                        () -> assertThat(savedJob.getId()).isEqualTo(savedJobId),
                        () -> assertThat(savedSections)
                                .extracting(section -> section.getName().getValue())
                                .containsAll(sectionCreateRequestNames),
                        () -> assertThat(savedTasks)
                                .extracting(task -> task.getName().getValue())
                                .containsAll(taskCreateRequestNames)
                );
            }
        }

        @Nested
        class 존재하지_않는_HostId를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final List<TaskCreateRequest> taskRequests = List.of(
                    new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                    new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            private final List<SectionCreateRequest> sectionRequests = List.of(
                    new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123",
                            taskRequests));
            private final JobCreateRequest request = new JobCreateRequest("청소", sectionRequests);

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.createJob(NON_EXIST_HOST_ID, space.getId(), request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_SpaceId를_입력받은_경우 {

            private static final long NON_EXIST_SPACE_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final List<TaskCreateRequest> taskRequests = List.of(
                    new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                    new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            private final List<SectionCreateRequest> sectionRequests = List.of(
                    new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123",
                            taskRequests));
            private final JobCreateRequest request = new JobCreateRequest("청소", sectionRequests);

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.createJob(host.getId(), NON_EXIST_SPACE_ID, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 공간입니다.");
            }
        }

        @Nested
        class 다른_Host의_SpaceId를_입력받은_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Host otherHost = repository.save(Host_생성("5678", 5678L));
            private final Space otherSpace = repository.save(Space_생성(otherHost, "잠실"));
            private final List<TaskCreateRequest> taskRequests = List.of(
                    new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                    new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            private final List<SectionCreateRequest> sectionRequests = List.of(
                    new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123",
                            taskRequests));
            private final JobCreateRequest request = new JobCreateRequest("청소", sectionRequests);

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.createJob(host.getId(), otherSpace.getId(), request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 공간입니다.");
            }
        }
    }

    @Nested
    class findJobs_메소드는 {

        @Nested
        class Job_목록이_존재하는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final List<Job> jobs = repository.saveAll(List.of(
                    Job_생성(space, "오픈"),
                    Job_생성(space, "청소"),
                    Job_생성(space, "마감")));
            private final List<String> jobNames = jobs.stream()
                    .map(job -> job.getName().getValue())
                    .collect(Collectors.toList());

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

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(NON_EXIST_HOST_ID, space.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Space의_Job_목록을_조회할_경우 {

            private static final long NON_EXIST_SPACE_ID = 0L;

            private final long hostId = repository.save(Host_생성("1234", 1234L))
                    .getId();

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(hostId, NON_EXIST_SPACE_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 공간입니다.");
            }
        }

        @Nested
        class 다른_Host의_Space의_Job_목록을_조회할_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Host otherHost = repository.save(Host_생성("1234", 2345L));
            private final Space otherHostSpace = repository.save(Space_생성(otherHost, "잠실"));

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> jobService.findJobs(host.getId(), otherHostSpace.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 공간입니다.");
            }
        }
    }

    @Nested
    class updateJob_메소드는 {

        @Nested
        class 기존의_Job이_존재하는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job originJob = repository.save(Job_생성(space, "마감"));
            private final Section originSection = repository.save(Section_생성(originJob, "소강의실"));
            private final Task originTask = repository.save(Task_생성(originSection, "불 끄기"));
            private final List<TaskCreateRequest> taskRequests = List.of(
                    new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                    new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            private final List<SectionCreateRequest> sectionRequests = List.of(
                    new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123",
                            taskRequests));
            private final JobCreateRequest request = new JobCreateRequest("청소", sectionRequests);

            @Test
            void Job을_수정한다() {
                jobService.updateJob(host.getId(), originJob.getId(), request);

                Job updateJob = repository.getById(Job.class, originJob.getId());
                List<Section> updateSections = sectionRepository.findAllByJob(updateJob);
                List<Task> updateTasks = taskRepository.findAllBySectionIn(updateSections);

                List<String> requestSectionNames = sectionRequests.stream()
                        .map(SectionCreateRequest::getName)
                        .collect(Collectors.toList());
                List<String> requestTaskNames = taskRequests.stream()
                        .map(TaskCreateRequest::getName)
                        .collect(Collectors.toList());

                assertAll(
                        () -> assertThat(updateJob.getName().getValue()).isEqualTo(request.getName()),
                        () -> assertThat(updateSections).doesNotContain(originSection),
                        () -> assertThat(updateTasks).doesNotContain(originTask),
                        () -> assertThat(updateSections)
                                .extracting(section -> section.getName().getValue())
                                .containsAll(requestSectionNames),
                        () -> assertThat(updateTasks)
                                .extracting(task -> task.getName().getValue())
                                .containsAll(requestTaskNames)
                );
            }
        }

        @Nested
        class 존재하지_않는_HostId를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job originJob = repository.save(Job_생성(space, "마감"));
            private final Section originSection = repository.save(Section_생성(originJob, "소강의실"));
            private final Task originTask = repository.save(Task_생성(originSection, "불 끄기"));
            private final List<TaskCreateRequest> taskRequests = List.of(
                    new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                    new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            private final List<SectionCreateRequest> sectionRequests = List.of(
                    new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123",
                            taskRequests));
            private final JobCreateRequest request = new JobCreateRequest("청소", sectionRequests);
            private final long savedJobId = jobService.createJob(host.getId(), space.getId(), request);

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.updateJob(NON_EXIST_HOST_ID, savedJobId, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 다른_Host의_JobId를_입력받은_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Host anotherHost = repository.save(Host_생성("1234", 2345L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job originJob = repository.save(Job_생성(space, "마감"));
            private final Section originSection = repository.save(Section_생성(originJob, "소강의실"));
            private final Task originTask = repository.save(Task_생성(originSection, "불 끄기"));
            private final List<TaskCreateRequest> taskRequests = List.of(
                    new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                    new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            private final List<SectionCreateRequest> sectionRequests = List.of(
                    new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123",
                            taskRequests));
            private final JobCreateRequest request = new JobCreateRequest("청소", sectionRequests);
            private final long savedJobId = jobService.createJob(host.getId(), space.getId(), request);

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.updateJob(anotherHost.getId(), savedJobId, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 존재하지_않는_JobId를_입력받을_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job originJob = repository.save(Job_생성(space, "마감"));
            private final Section originSection = repository.save(Section_생성(originJob, "소강의실"));
            private final Task originTask = repository.save(Task_생성(originSection, "불 끄기"));
            private final List<TaskCreateRequest> taskRequests = List.of(
                    new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                    new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            private final List<SectionCreateRequest> sectionRequests = List.of(
                    new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123",
                            taskRequests));
            private final JobCreateRequest request = new JobCreateRequest("청소", sectionRequests);
            private final long savedJobId = jobService.createJob(host.getId(), space.getId(), request);

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.updateJob(host.getId(), NON_EXIST_JOB_ID, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
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
                Host host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.removeJob(NON_EXIST_HOST_ID, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class Job이_존재하지_않는_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private Host host;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.removeJob(host.getId(), NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_Job_제거할_경우 {

            private Job job;
            private Host otherHost;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                otherHost = repository.save(Host_생성("1234", 2345L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "청소"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.removeJob(otherHost.getId(), job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
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
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실 캠퍼스"));
                job = repository.save(Job_생성(space, "청소"));
                section = repository.save(Section_생성(job, "대강의실"));
                task = repository.save(Task_생성(section, "책상 닦기"));
                runningTask = repository.save(RunningTask_생성(task.getId(), false));
            }

            @Test
            void Job과_관련된_Section_Task_RunningTask를_함께_삭제한다() {
                jobService.removeJob(host.getId(), job.getId());

                assertAll(
                        () -> assertThat(repository.findById(Job.class, job.getId())).isEmpty(),
                        () -> assertThat(repository.findById(Section.class, section.getId())).isEmpty(),
                        () -> assertThat(repository.findById(Task.class, task.getId())).isEmpty(),
                        () -> assertThat(repository.findById(RunningTask.class, runningTask.getTaskId())).isEmpty()
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
                            .hasMessageContaining("존재하지 않는 호스트입니다.");
                }
            }

            @Nested
            class Job이_존재하지_않는_경우 {

                private static final long NON_EXIST_JOB_ID = 0L;

                private Host host;

                @BeforeEach
                void setUp() {
                    host = repository.save(Host_생성("1234", 1234L));
                }

                @Test
                void 예외가_발생한다() {
                    assertThatThrownBy(() -> jobService.findSlackUrl(host.getId(), NON_EXIST_JOB_ID))
                            .isInstanceOf(NotFoundException.class)
                            .hasMessageContaining("존재하지 않는 작업입니다.");
                }
            }
        }

        @Nested
        class Job이_존재하는데_다른_Host의_Job의_Slack_Url_조회하는_경우 {

            private Host myHost;
            private Job otherJob;

            @BeforeEach
            void setUp() {
                myHost = repository.save(Host_생성("1234", 1234L));
                Host otherHost = repository.save(Host_생성("1234", 2456L));
                Space otherSpace = repository.save(Space_생성(otherHost, "잠실"));
                otherJob = repository.save(Job_생성(otherSpace, "톱오브스윙방"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.findSlackUrl(myHost.getId(), otherJob.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class Job이_존재하는_경우 {

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실"));
                job = repository.save(Job_생성(space, "톱오브스윙방", "http://slackurl.com"));
            }

            @Test
            void Slack_Url을_조회한다() {
                SlackUrlResponse actual = jobService.findSlackUrl(host.getId(), job.getId());

                assertThat(actual.getSlackUrl()).isEqualTo("http://slackurl.com");
            }
        }
    }

    @Nested
    class findSlackUrl_메소드는 {

        @Nested
        class 존재하는_JobId를_입력받는_경우 {

            private static final String SLACK_URL = "http://slackurl.com";

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "톱오브스윙방", SLACK_URL));

            @Test
            void Slack_Url을_반환한다() {
                String actual = jobService.findSlackUrl(host.getId(), job.getId())
                        .getSlackUrl();

                assertThat(actual).isEqualTo(SLACK_URL);
            }
        }

        @Nested
        class 존재하지_않는_HostId를_입력받는_경우 {

            private static final String SLACK_URL = "http://slackurl.com";
            private static final long NON_EXIST_HOST_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "톱오브스윙방", SLACK_URL));

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.findJobs(NON_EXIST_HOST_ID, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_JobId를_입력받는_경우 {

            private static final String SLACK_URL = "http://slackurl.com";
            private static final long NON_EXIST_JOB_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "톱오브스윙방", SLACK_URL));

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.findSlackUrl(host.getId(), NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_JobId를_입력_받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Host otherHost = repository.save(Host_생성("1234", 2456L));
            private final Space otherHostSpace = repository.save(Space_생성(otherHost, "잠실"));
            private final Job otherHostJob = repository.save(Job_생성(otherHostSpace, "톱오브스윙방"));

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.findSlackUrl(host.getId(), otherHostJob.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class changeSlackUrl_메소드는 {

        @Nested
        class 존재하는_Job을_입력_받는_경우 {

            private static final String SLACK_URL = "http://slackurl.com";
            private static final String NEW_SLACK_URL = "https://newslackurl.com";

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "톱오브스윙방", SLACK_URL));
            private final SlackUrlChangeRequest request = new SlackUrlChangeRequest(NEW_SLACK_URL);

            @Test
            void Slack_Url을_수정한다() {
                jobService.changeSlackUrl(host.getId(), job.getId(), request);
                Job actual = repository.getById(Job.class, job.getId());

                assertThat(actual.getSlackUrl()).isEqualTo(NEW_SLACK_URL);
            }
        }

        @Nested
        class 존재하지_않는_Host를_입력_받는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;
            private static final long JOB_ID = 1L;

            private final SlackUrlChangeRequest request = new SlackUrlChangeRequest("https://newslackurl.com");

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.changeSlackUrl(NON_EXIST_HOST_ID, JOB_ID, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Job을_입력_받는_경우 {

            private static final long NON_EXIST_JOB_ID = 0L;

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final SlackUrlChangeRequest request = new SlackUrlChangeRequest("https://newslackurl.com");

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.changeSlackUrl(host.getId(), NON_EXIST_JOB_ID, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 다른_Host의_JobId를_입력_받는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Host otherHost = repository.save(Host_생성("1234", 2456L));
            private final Space otherHostSpace = repository.save(Space_생성(otherHost, "잠실"));
            private final Job otherHostJob = repository.save(Job_생성(otherHostSpace, "톱오브스윙방"));
            private final SlackUrlChangeRequest request = new SlackUrlChangeRequest("https://newslackurl.com");

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobService.changeSlackUrl(host.getId(), otherHostJob.getId(), request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }
    }
}
