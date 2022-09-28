package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.ApplicationTest;
import com.woowacourse.gongcheck.SupportRepository;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.submission.Submission;
import com.woowacourse.gongcheck.core.domain.submission.SubmissionRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskSseEmitterContainer;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.presentation.request.SubmissionRequest;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@ApplicationTest
class UserLockSubmissionServiceTest {

    @Autowired
    private UserLockSubmissionService userLockSubmissionService;

    @Autowired
    private SupportRepository repository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private RunningTaskRepository runningTaskRepository;

    @MockBean
    private RunningTaskSseEmitterContainer runningTaskSseEmitterContainer;

    @Nested
    class submitJobCompletionByLock_메소드는 {

        @Nested
        class 사용자_100명이_동시에_요청하는_경우 {

            private final Host host = repository.save(Host_생성("1234", 1234L));
            private final Space space = repository.save(Space_생성(host, "잠실"));
            private final Job job = repository.save(Job_생성(space, "청소"));
            private final SubmissionRequest request = new SubmissionRequest("제출자");

            private final int threadCount = 11;
            private final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

            @BeforeEach
            void setUp() {
                Section section = repository.save(Section_생성(job, "트랙룸"));
                Task task_1 = repository.save(Task_생성(section, "책상 청소"));
                Task task_2 = repository.save(Task_생성(section, "의자 넣기"));
                repository.save(RunningTask_생성(task_1.getId(), true));
                repository.save(RunningTask_생성(task_2.getId(), true));
            }

            @Test
            void
            한명만_제출된다() throws InterruptedException {
                runThreadPool(
                        () -> userLockSubmissionService.submitJobCompletionByLock(host.getId(), job.getId(), request));
                List<Submission> submissions = submissionRepository.findAll();
                int runningTaskSize = runningTaskRepository.findAll()
                        .size();

                assertAll(
                        () -> assertThat(submissions).hasSize(1),
                        () -> assertThat(submissions.get(0).getAuthor()).isEqualTo(request.getAuthor()),
                        () -> assertThat(runningTaskSize).isZero()
                );
            }

            private void runThreadPool(Runnable runnable) throws InterruptedException {
                CountDownLatch latch = new CountDownLatch(threadCount);
                for (int i = 0; i < threadCount; i++) {
                    executorService.submit(() -> {
                        try {
                            runnable.run();
                        } finally {
                            latch.countDown();
                        }
                    });
                }
                latch.await();
            }
        }
    }
}
