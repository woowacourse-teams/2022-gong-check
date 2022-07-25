package com.woowacourse.gongcheck.core.domain.job;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
class JobRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Test
    void Job_저장_시_생성시간이_저장된다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));

        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        Job job = jobRepository.save(Job.builder()
                .space(space)
                .name("청소")
                .build());
        assertThat(job.getCreatedAt()).isAfter(nowLocalDateTime);
    }

    @Test
    void Host와_Space를_입력받아_연관되는_Job을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job1 = Job_생성(space, "오픈");
        Job job2 = Job_생성(space, "청소");
        Job job3 = Job_생성(space, "마감");
        jobRepository.saveAll(List.of(job1, job2, job3));

        List<Job> result = jobRepository.findAllBySpaceHostAndSpace(host, space);

        assertThat(result).hasSize(3);
    }

    @Test
    void Host와_JobId를_입력받아_Job을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        Job result = jobRepository.getBySpaceHostAndId(host, job.getId());

        assertThat(result).isEqualTo(job);
    }

    @Test
    void 입력받은_Host와_JobId가_연관되지_않은_경우_예외가_발생한다() {
        Host host1 = hostRepository.save(Host_생성("1234", 1234L));
        Host host2 = hostRepository.save(Host_생성("1234", 2345L));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        assertThatThrownBy(() -> jobRepository.getBySpaceHostAndId(host1, job.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 입력된_Space에_등록된_모든_Job을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실 캠퍼스"));
        Job job1 = jobRepository.save(Job_생성(space, "청소"));
        Job job2 = jobRepository.save(Job_생성(space, "마감"));

        List<Job> result = jobRepository.findAllBySpace(space);

        assertThat(result).containsExactly(job1, job2);
    }

    @Test
    void jobId로_Job을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        Job savedJob = jobRepository.getById(job.getId());

        assertThat(savedJob).isNotNull();
    }

    @Test
    void 존재하지_않는_jobId로_Job을_조회_시_예외가_발생한다() {
        assertThatThrownBy(() -> jobRepository.getById(0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }
}
