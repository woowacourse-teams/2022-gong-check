package com.woowacourse.gongcheck.domain.job;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
class JobRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Test
    void 멤버와_공간으로_조회한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job1 = Job_생성(space, "오픈");
        Job job2 = Job_생성(space, "청소");
        Job job3 = Job_생성(space, "마감");
        jobRepository.saveAll(List.of(job1, job2, job3));

        Slice<Job> result = jobRepository.findBySpaceHostAndSpace(host, space, PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getSize()).isEqualTo(2),
                () -> assertThat(result.hasNext()).isTrue()
        );
    }

    @Test
    void 멤버와_아이디로_작업을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        Job result = jobRepository.getBySpaceHostAndId(host, job.getId());

        assertThat(result).isEqualTo(job);
    }

    @Test
    void 다른_호스트의_작업을_조회할_경우_예외가_발생한다() {
        Host host1 = hostRepository.save(Host_생성("1234"));
        Host host2 = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        assertThatThrownBy(() -> jobRepository.getBySpaceHostAndId(host1, job.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }
}
