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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
@Import(JpaConfig.class)
class SubmissionRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Test
    void Submission_저장_시_생성시간이_저장된다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        Submission submission = submissionRepository.save(Submission.builder()
                .job(job)
                .author("어썸오")
                .build());
        assertThat(submission.getCreatedAt()).isAfter(nowLocalDateTime);
    }

    @Test
    void 입력받은_Job_목록에_해당하는_Submission_목록을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job1 = jobRepository.save(Job_생성(space, "오픈"));
        Job job2 = jobRepository.save(Job_생성(space, "마감"));
        submissionRepository.save(Submission_생성(job1));
        submissionRepository.save(Submission_생성(job1));
        submissionRepository.save(Submission_생성(job1));
        submissionRepository.save(Submission_생성(job1));

        Slice<Submission> submissions = submissionRepository
                .findAllByJobIn(List.of(job1, job2), PageRequest.of(0, 3));

        assertAll(
                () -> assertThat(submissions.getSize()).isEqualTo(3),
                () -> assertThat(submissions.hasNext()).isTrue()
        );
    }
}