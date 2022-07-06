package com.woowacourse.gongcheck.domain.task;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.section.SectionRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
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
        Host host = hostRepository.save(Host_생성("1234"));
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
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section1 = sectionRepository.save(Section_생성(job, "트랙룸"));
        Task task = taskRepository.save(Task_생성(section1, "책상 청소"));

        Task result = taskRepository.findBySectionJobSpaceHostAndId(host, task.getId()).get();

        assertThat(result).isEqualTo(task);
    }
}
