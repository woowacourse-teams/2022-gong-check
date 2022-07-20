package com.woowacourse.gongcheck.domain.section;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SectionRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    void 입력된_Job_목록에_해당하는_모든_Section을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job1 = jobRepository.save(Job_생성(space, "청소"));
        Job job2 = jobRepository.save(Job_생성(space, "마감"));
        Section section1 = sectionRepository.save(Section_생성(job1, "트랙룸"));
        Section section2 = sectionRepository.save(Section_생성(job1, "굿샷 강의장"));
        Section section3 = sectionRepository.save(Section_생성(job2, "트랙룸"));
        Section section4 = sectionRepository.save(Section_생성(job2, "굿샷 강의장"));

        List<Section> result = sectionRepository.findAllByJobIn(List.of(job1, job2));

        assertThat(result).containsExactly(section1, section2, section3, section4);
    }
}
