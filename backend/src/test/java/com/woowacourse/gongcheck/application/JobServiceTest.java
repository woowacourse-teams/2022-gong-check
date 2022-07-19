package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.application.response.JobsResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.presentation.request.TaskCreateRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JobServiceTest {

    @Autowired
    private JobService jobService;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Test
    void Job_목록을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job1 = Job_생성(space, "오픈");
        Job job2 = Job_생성(space, "청소");
        Job job3 = Job_생성(space, "마감");
        jobRepository.saveAll(List.of(job1, job2, job3));

        JobsResponse result = jobService.findPage(host.getId(), space.getId(), PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getJobs()).hasSize(2),
                () -> assertThat(result.isHasNext()).isTrue()
        );
    }

    @Test
    void 존재하지_않는_Host로_Job_목록을_조회할_경우_예외를_던진다() {
        assertThatThrownBy(() -> jobService.findPage(0L, 1L, PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 존재하지_않는_Space의_Job_목록을_조회할_경우_예외를_던진다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        assertThatThrownBy(() -> jobService.findPage(host.getId(), 0L, PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }

    @Test
    void 다른_Host의_Space의_Job_목록을_조회할_경우_예외를_던진다() {
        Host host1 = hostRepository.save(Host_생성("1234", 1234L));
        Host host2 = hostRepository.save(Host_생성("1234", 2345L));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));

        assertThatThrownBy(() -> jobService.findPage(host1.getId(), space.getId(), PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }

    @Test
    void Job과_Section들과_Task들을_한_번에_생성한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest jobCreateRequest = new JobCreateRequest("청소", sections);

        Long savedJobId = jobService.createJob(host.getId(), space.getId(), jobCreateRequest);

        assertThat(savedJobId).isNotNull();
    }

    @Test
    void Host가_존재하지_않는데_Job_생성_시_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest jobCreateRequest = new JobCreateRequest("청소", sections);

        assertThatThrownBy(() -> jobService.createJob(0L, space.getId(), jobCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void Space가_존재하지_않는데_Job_생성_시_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest jobCreateRequest = new JobCreateRequest("청소", sections);

        assertThatThrownBy(() -> jobService.createJob(host.getId(), 0L, jobCreateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }

    @Test
    void Host가_존재하지_않는데_Slack_Url_조회_시_예외가_발생한다() {
        assertThatThrownBy(() -> jobService.findSlackUrl(0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }
}
