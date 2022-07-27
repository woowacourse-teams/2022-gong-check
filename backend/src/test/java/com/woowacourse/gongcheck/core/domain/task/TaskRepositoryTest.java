package com.woowacourse.gongcheck.core.domain.task;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.section.SectionRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
@DisplayName("TaskRepository 클래스")
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
        class 존재하는_Host의_Space의_Job의_Section에 {

            private static final String TASK_NAME = "책상 청소";

            private Section section;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                section = sectionRepository.save(Section_생성(job, "트랙룸"));
            }

            @Test
            void Task를_저장한다() {
                Task task = taskRepository.save(Task_생성(section, TASK_NAME));

                assertAll(
                        () -> assertThat(task.getId()).isNotNull(),
                        () -> assertThat(task.getName()).isEqualTo(TASK_NAME),
                        () -> assertThat(task.getCreatedAt()).isBefore(LocalDateTime.now())
                );
            }
        }
    }

    @Nested
    class findAllBySectionJob_메소드는 {

        @Nested
        class 존재하는_Host의_Space의_Job의_Task를_조회할_때 {

            private Job job;
            private List<Task> tasks;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                job = jobRepository.save(Job_생성(space, "청소"));
                Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
                Section section2 = sectionRepository.save(Section_생성(job, "굿샷 강의장"));
                tasks = List.of(
                        Task_생성(section1, "책상 청소"),
                        Task_생성(section1, "빈백 정리"),
                        Task_생성(section2, "책상 청소"),
                        Task_생성(section2, "의자 넣기")
                );
                taskRepository.saveAll(tasks);
            }

            @Test
            void 모든_Task를_조회한다() {
                List<Task> result = taskRepository.findAllBySectionJob(job);

                assertThat(result).hasSize(tasks.size());
            }
        }
    }

    @Nested
    class getBySectionJobSpaceHostAndId_메소드는 {

        @Nested
        class 존재하는_Host와_TaskId를_받으면 {

            private Host host;
            private Task task;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
                task = taskRepository.save(Task_생성(section1, "책상 청소"));
            }

            @Test
            void 해당_Host의_Task를_조회한다() {
                Task result = taskRepository.getBySectionJobSpaceHostAndId(host, task.getId());

                assertThat(result).isEqualTo(task);
            }
        }

        @Nested
        class 존재하지_않는_TaskId를_받으면 {

            private Host host;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", 1234L));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskRepository.getBySectionJobSpaceHostAndId(host, 0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }

        @Nested
        class 해당_Host의_Task가_아닌_TaskId를_받으면 {

            private Host anotherHost;
            private Task task;

            @BeforeEach
            void setUp() {
                anotherHost = hostRepository.save(Host_생성("1234", 2345L));
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
                task = taskRepository.save(Task_생성(section, "책상 청소"));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> taskRepository.getBySectionJobSpaceHostAndId(anotherHost, task.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 작업입니다.");
            }
        }
    }

    @Nested
    class findAllBySectionIn_메소드는 {

        @Nested
        class Section_목록을_받으면 {

            private Section section1;
            private Section section2;
            private List<Task> tasks;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
                section2 = sectionRepository.save(Section_생성(job, "트랙룸"));
                tasks = List.of(
                        Task_생성(section1, "책상 청소"),
                        Task_생성(section1, "빈백 정리"),
                        Task_생성(section2, "책상 청소"),
                        Task_생성(section2, "의자 넣기")
                );
                taskRepository.saveAll(tasks);
            }

            @Test
            void 해당하는_모든_Task를_조회한다() {
                List<Task> result = taskRepository.findAllBySectionIn(List.of(section1, section2));

                assertThat(result).containsAll(tasks);
            }
        }
    }

    @Nested
    class deleteAllBySectionIn_메소드는 {

        @Nested
        class Section_목록을_받으면 {

            private Section section;
            private Task task;

            @BeforeEach
            void setUp() {
                Host host = hostRepository.save(Host_생성("1234", 1234L));
                Space space = spaceRepository.save(Space_생성(host, "잠실"));
                Job job = jobRepository.save(Job_생성(space, "청소"));
                section = sectionRepository.save(Section_생성(job, "트랙룸"));
                task = taskRepository.save(Task_생성(section, "책상 청소"));
            }

            @Test
            void 해당하는_모든_Task를_삭제한다() {
                taskRepository.deleteAllBySectionIn(List.of(section));

                assertThat(taskRepository.findById(task.getId())).isEmpty();
            }
        }
    }
}
