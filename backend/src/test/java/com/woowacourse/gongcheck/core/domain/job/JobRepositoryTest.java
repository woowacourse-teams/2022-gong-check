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
@DisplayName("JobRepositoryTest 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JobRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Nested
    class save_메소드는 {

        @Nested
        class Job_저장할_경우 {

            private LocalDateTime nowLocalDateTime;
            private Job job;
            private Space space;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실"));

                nowLocalDateTime = LocalDateTime.now();
            }

            @Test
            void 생성시간이_저장된다() {
                job = jobRepository.save(Job.builder()
                        .space(space)
                        .name(new Name("청소"))
                        .build());
                assertThat(job.getCreatedAt()).isAfter(nowLocalDateTime);
            }
        }
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

                assertThat(result).hasSize(3);
            }
        }
    }

    @Nested
    class getBySpaceHostAndId_메소드는 {

        @Nested
        class Host와_JobId를_입력받는_경우 {

            private Host host;
            private Job job;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
            }

            @Test
            void 해당되는_Job을_조회한다() {
                Job result = jobRepository.getBySpaceHostAndId(host, job.getId());

                assertThat(result).isEqualTo(job);
            }
        }

        @Nested
        class 입력받은_Host와_JobId가_연관되지_않은_경우 {

            private Host otherHost;
            private Job job;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                otherHost = hostRepository.save(Host_생성("1234", 2345L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobRepository.getBySpaceHostAndId(otherHost, job.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class findAllBySpace_메소드는 {

        @Nested
        class Space를_입력_받는_경우 {

            private Space space;
            private List<Job> jobs;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                space = spaceRepository.save(Space_생성(host, "잠실 캠퍼스"));
                jobs = jobRepository.saveAll(List.of(Job_생성(space, "청소"), Job_생성(space, "마감")));
            }

            @Test
            void 연관된_모든_Job_목록을_조회한다() {
                List<Job> result = jobRepository.findAllBySpace(space);

                assertThat(result).containsExactlyElementsOf(jobs);
            }
        }
    }

    @Nested
    class getById_메소드는 {

        @Nested
        class jobId를_입력_받는_경우 {

            private Job job;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
            }

            @Test
            void Job을_조회한다() {
                Job savedJob = jobRepository.getById(job.getId());

                assertThat(savedJob).isNotNull();
            }
        }

        @Nested
        class 존재하지_않는_jobId를_입력_받는_경우 {
            private final static long NON_EXIST_JOB_ID = 0L;

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> jobRepository.getById(NON_EXIST_JOB_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }
    }
}
