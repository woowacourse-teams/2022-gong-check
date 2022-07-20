package com.woowacourse.gongcheck.domain.task;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.section.SectionRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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

    @Test
    void Job이_가진_모든_Task를_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
        Section section2 = sectionRepository.save(Section_생성(job, "굿샷 강의장"));
        taskRepository.saveAll(List.of(Task_생성(section1, "책상 청소"), Task_생성(section1, "빈백 정리")));
        taskRepository.saveAll(List.of(Task_생성(section2, "책상 청소"), Task_생성(section2, "의자 넣기")));

        List<Task> result = taskRepository.findAllBySectionJob(job);

        assertThat(result).hasSize(4);
    }

    @Test
    void Host와_TaskId를_입력_받아_Task를_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = taskRepository.save(Task_생성(section1, "책상 청소"));

        Task result = taskRepository.getBySectionJobSpaceHostAndId(host, task.getId());

        assertThat(result).isEqualTo(task);
    }

    @Test
    void 입력받은_TaskId에_해당하는_Task가_없는_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        assertThatThrownBy(() -> taskRepository.getBySectionJobSpaceHostAndId(host, 0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 입력받은_Host에_해당하는_Task가_없는_경우_예외가_발생한다() {
        Host host1 = hostRepository.save(Host_생성("1234", 1234L));
        Host host2 = hostRepository.save(Host_생성("1234", 2345L));
        Space space = spaceRepository.save(Space_생성(host1, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = taskRepository.save(Task_생성(section1, "책상 청소"));

        assertThatThrownBy(() -> taskRepository.getBySectionJobSpaceHostAndId(host2, task.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 입력받은_Section_목록에_해당하는_모든_Task를_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
        Section section2 = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task1 = taskRepository.save(Task_생성(section1, "책상 청소"));
        Task task2 = taskRepository.save(Task_생성(section1, "빈백 정리"));
        Task task3 = taskRepository.save(Task_생성(section2, "책상 청소"));
        Task task4 = taskRepository.save(Task_생성(section2, "빈백 정리"));

        List<Task> result = taskRepository.findAllBySectionIn(List.of(section1, section2));

        assertThat(result).containsExactly(task1, task2, task3, task4);
    }

    @Test
    void Section을_통해_Task_리스트를_삭제한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = taskRepository.save(Task_생성(section, "책상 청소"));

        taskRepository.deleteAllBySection(section);

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }
}
