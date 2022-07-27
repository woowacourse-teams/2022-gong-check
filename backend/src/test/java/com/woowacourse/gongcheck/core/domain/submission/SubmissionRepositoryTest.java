package com.woowacourse.gongcheck.core.domain.submission;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Submission_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
@Import(JpaConfig.class)
@DisplayName("SubmissionRepository 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SubmissionRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Nested
    class save_메소드는 {

        @Nested
        class 호출_되는_경우 {

            private Submission submission;
            private LocalDateTime expected;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                submission = submissionRepository.save(Submission.builder()
                        .job(job)
                        .author("어썸오")
                        .build());
                expected = LocalDateTime.now();
            }

            @Test
            void 생성시간을_저장한다() {
                assertThat(submission.getCreatedAt()).isBefore(expected);
            }
        }
    }

    @Nested
    class findAllByJobIn_메소드는 {

        @Nested
        class Job들의_리스트를_입력받는_경우 {

            private List<Job> jobs;
            private PageRequest request;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job_1 = jobRepository.save(Job_생성(space, "청소"));
                Job job_2 = jobRepository.save(Job_생성(space, "마감"));
                jobs = jobRepository.saveAll(List.of(job_1, job_2));
                submissionRepository.saveAll(List.of(Submission_생성(job_1), Submission_생성(job_1),
                        Submission_생성(job_2), Submission_생성(job_2)));
                request = PageRequest.of(0, 2);
            }

            @Test
            void 입력받은_Job들의_Submission들을_반환한다() {
                Slice<Submission> result = submissionRepository.findAllByJobIn(jobs, request);

                assertAll(
                        () -> assertThat(result.getContent()).hasSize(2),
                        () -> assertThat(result.hasNext()).isTrue()
                );
            }
        }
    }
}
