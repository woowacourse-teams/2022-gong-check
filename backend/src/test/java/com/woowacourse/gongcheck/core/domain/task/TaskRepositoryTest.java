package com.woowacourse.gongcheck.core.domain.task;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.section.SectionRepository;
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
@DisplayName("TaskRepository 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TaskRepositoryTest {

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

    @Nested
    class save_메소드는 {

        @Nested
        class 입력받은_Task를_저장할_떄 {

            private Task task;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                task = Task_생성(section, "책상 청소");
            }

            @Test
            void 생성_시간도_함께_저장한다() {
                LocalDateTime timeThatBeforeSave = LocalDateTime.now();
                Task actual = taskRepository.save(task);
                assertThat(actual.getCreatedAt()).isAfter(timeThatBeforeSave);
            }
        }

        @Nested
        class 입력받은_Task의_Description이_null인_경우 {

            private Section section;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                section = sectionRepository.save(Section_생성(job, "트랙룸"));
            }

            @Test
            void 정상적으로_Task를_저장한다() {
                Task task = Task.builder()
                        .name(new Name("책상 닦기"))
                        .section(section)
                        .build();
                assertThatCode(() -> taskRepository.save(task))
                        .doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class findAllBySectionJob_메소드는 {

        @Nested
        class 입력받은_Job이_Task를_가지고_있는_경우 {

            private Job job;
            private List<Task> expected;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
                Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
                Section section2 = sectionRepository.save(Section_생성(job, "굿샷 강의장"));
                expected = List.of(
                        Task_생성(section1, "책상 청소"), Task_생성(section1, "빈백 정리"),
                        Task_생성(section2, "책상 청소"), Task_생성(section2, "의자 넣기")
                );
                taskRepository.saveAll(expected);
            }

            @Test
            void 가지고_있는_모든_Task를_반환한다() {
                List<Task> actual = taskRepository.findAllBySectionJob(job);

                assertThat(actual).hasSize(expected.size());
            }
        }
    }

    @Nested
    class getBySectionJobSpaceHostAndId_메소드는 {

        @Nested
        class 존재하는_Host와_TaskId를_받으면 {

            private Host host;
            private Task expected;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
                expected = taskRepository.save(Task_생성(section1, "책상 청소"));
            }

            @Test
            void Task를_반환한다() {
                Task actual = taskRepository.getBySectionJobSpaceHostAndId(host, expected.getId());

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 존재하지_않는_TaskId를_받으면 {

            private static final long NON_EXIST_TASK_ID = 0L;

            private Host host;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskRepository.getBySectionJobSpaceHostAndId(host, NON_EXIST_TASK_ID))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 해당_Host의_Task가_아닌_TaskId를_받으면 {

            private Host anotherHost;
            private Long taskId;

            @BeforeEach
            void setUp() {
                anotherHost = hostRepository.save(Host_생성("1234", 2345L));
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                taskId = taskRepository.save(Task_생성(section, "책상 청소"))
                        .getId();
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskRepository.getBySectionJobSpaceHostAndId(anotherHost, taskId))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class findAllBySectionIn_메소드는 {

        @Nested
        class Section_목록을_받으면 {

            private Section section1;
            private Section section2;
            private List<Task> expected;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
                section2 = sectionRepository.save(Section_생성(job, "트랙룸"));
                expected = List.of(
                        Task_생성(section1, "책상 청소"), Task_생성(section1, "빈백 정리"),
                        Task_생성(section2, "책상 청소"), Task_생성(section2, "의자 넣기"));
                taskRepository.saveAll(expected);
            }

            @Test
            void Section에_해당하는_모든_Task를_조회한다() {
                List<Task> actual = taskRepository.findAllBySectionIn(List.of(section1, section2));

                assertThat(actual).hasSize(expected.size());
            }
        }
    }

    @Nested
    class deleteAllBySectionIn_메소드는 {

        @Nested
        class Section_목록을_받으면 {

            private Section section;
            private Long taskId;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                section = sectionRepository.save(Section_생성(job, "트랙룸"));
                taskId = taskRepository.save(Task_생성(section, "책상 청소"))
                        .getId();
            }

            @Test
            void Section에_해당하는_모든_Task를_삭제한다() {
                taskRepository.deleteAllBySectionIn(List.of(section));

                assertThat(taskRepository.findById(taskId)).isEmpty();
            }
        }
    }

    @Nested
    class findAllBySection_메소드는 {

        @Nested
        class 입력받은_Job이_Task를_가지고_있는_경우 {

            private Job job;
            private Section expectedSection1;
            private Section expectedSection2;
            private List<Task> expected;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
                expectedSection1 = sectionRepository.save(Section_생성(job, "트랙룸"));
                expectedSection2 = sectionRepository.save(Section_생성(job, "굿샷 강의장"));
                expected = List.of(
                        Task_생성(expectedSection1, "책상 청소"), Task_생성(expectedSection1, "빈백 정리"),
                        Task_생성(expectedSection2, "책상 청소"), Task_생성(expectedSection2, "의자 넣기")
                );
                taskRepository.saveAll(expected);
            }

            @Test
            void 가지고_있는_모든_Task를_반환한다() {
                List<Task> actual = taskRepository.findAllBySectionJob(job);

                assertThat(actual).hasSize(expected.size())
                        .extracting("section", "name")
                        .containsExactly(tuple(expectedSection1, new Name("책상 청소")),
                                tuple(expectedSection1, new Name("빈백 정리")),
                                tuple(expectedSection2, new Name("책상 청소")),
                                tuple(expectedSection2, new Name("의자 넣기")));
            }
        }
    }
}
