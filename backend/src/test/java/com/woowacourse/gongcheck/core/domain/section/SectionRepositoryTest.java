package com.woowacourse.gongcheck.core.domain.section;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.core.domain.vo.Name;
import com.woowacourse.gongcheck.exception.NotFoundException;
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

@DataJpaTest
@Import(JpaConfig.class)
@DisplayName("SectionRepositoryTest 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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
    void Section_저장_시_생성시간이_저장된다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        Section section = sectionRepository.save(Section.builder()
                .job(job)
                .name(new Name("트랙룸"))
                .build());
        assertThat(section.getCreatedAt()).isAfter(nowLocalDateTime);
    }

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

    @Test
    void Job에_해당하는_모든_Section을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
        Section section2 = sectionRepository.save(Section_생성(job, "굿샷 강의장"));

        List<Section> result = sectionRepository.findAllByJob(job);

        assertThat(result).containsExactly(section1, section2);
    }

    @Nested
    class findAllBySpaceHostAndSpace_메소드는 {

        @Nested
        class Host와_Space를_입력받은_경우 {

            private Host host;
            private Space space;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job1 = Job_생성(space, "오픈");
                Job job2 = Job_생성(space, "청소");
                Job job3 = Job_생성(space, "마감");
                jobRepository.saveAll(List.of(job1, job2, job3));
            }

            @Test
            void 연관된_Job_목록을_조회한다() {
                List<Job> result = jobRepository.findAllBySpaceHostAndSpace(host, space);

                assertThat(result).hasSize(3)
                        .extracting("name")
                        .extracting("value")
                        .containsExactly("오픈", "청소", "마감");
            }
        }
    }

    @Nested
    class getByJobSpaceHostAndId_메소드는 {

        @Nested
        class Host와_SectionId를_입력받는_경우 {

            private Host host;
            private Section section;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                section = sectionRepository.save(Section_생성(job, "대강의실"));
            }

            @Test
            void 해당되는_Section을_조회한다() {
                Section result = sectionRepository.getByJobSpaceHostAndId(host, section.getId());

                assertThat(result).isEqualTo(section);
            }
        }

        @Nested
        class 입력받은_Host와_SectionId가_연관되지_않은_경우 {

            private Host otherHost;
            private Section section;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                otherHost = hostRepository.save(Host_생성("1234", 2345L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                section = sectionRepository.save(Section_생성(job, "대강의실"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> sectionRepository.getByJobSpaceHostAndId(otherHost, section.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 구역입니다.");
            }
        }
    }
}
