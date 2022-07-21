package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.application.response.SpaceResponse;
import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.section.SectionRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.domain.task.RunningTask;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.domain.task.Task;
import com.woowacourse.gongcheck.domain.task.TaskRepository;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.presentation.request.SpaceCreateRequest;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SpaceServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private SpaceService spaceService;

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

    @Autowired
    private RunningTaskRepository runningTaskRepository;

    @Nested
    class Space_조회 {

        @Test
        void Space를_조회한다() {
            Host host = hostRepository.save(Host_생성("1234", 1234L));
            Space space1 = Space_생성(host, "잠실 캠퍼스");
            Space space2 = Space_생성(host, "선릉 캠퍼스");
            Space space3 = Space_생성(host, "양평같은방");
            spaceRepository.saveAll(List.of(space1, space2, space3));

            SpacesResponse result = spaceService.findSpaces(host.getId());

            assertThat(result.getSpaces()).hasSize(3);
        }

        @Test
        void 존재하지_않는_Host로_Space를_조회할_경우_예외가_발생한다() {
            assertThatThrownBy(() -> spaceService.findSpaces(0L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }
    }

    @Nested
    class Space_생성 {

        @Test
        void Space를_생성한다() {
            Host host = hostRepository.save(Host_생성("1234", 1234L));
            SpaceCreateRequest spaceCreateRequest = new SpaceCreateRequest("잠실 캠퍼스",
                    new MockMultipartFile("잠실 캠퍼스 사진", new byte[]{}));
            Long spaceId = spaceService.createSpace(host.getId(), spaceCreateRequest);

            assertThat(spaceId).isNotNull();
        }

        @Test
        void 이미_존재하는_Space_이름을_입력할_경우_예외가_발생한다() {
            Host host = hostRepository.save(Host_생성("1234", 1234L));
            String spaceName = "잠실 캠퍼스";
            Space space = Space_생성(host, spaceName);
            spaceRepository.save(space);

            SpaceCreateRequest request = new SpaceCreateRequest(spaceName,
                    new MockMultipartFile("잠실 캠퍼스 사진", new byte[]{}));

            assertThatThrownBy(() -> spaceService.createSpace(host.getId(), request))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("이미 존재하는 이름입니다.");
        }

        @Test
        void 존재하지_않는_Host로_생성하려는_경우_예외가_발생한다() {
            SpaceCreateRequest request = new SpaceCreateRequest("잠실 캠퍼스",
                    new MockMultipartFile("잠실 캠퍼스 사진", new byte[]{}));

            assertThatThrownBy(() -> spaceService.createSpace(0L, request))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }
    }

    @Nested
    class 단일_Space_조회 {

        private Host host;
        private Space space1;
        private Space space2;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            space1 = Space_생성(host, "잠실 캠퍼스");
            space2 = Space_생성(host, "선릉 캠퍼스");
            spaceRepository.saveAll(List.of(space1, space2));
        }

        @Test
        void HostId와_SpaceId로_단일_Space를_조회한다() {
            SpaceResponse response = spaceService.findSpace(host.getId(), space1.getId());

            assertThat(response.getName()).isEqualTo(space1.getName());
        }

        @Test
        void 다른_HostId로_단일_Space를_조회할_경우_예외가_발생한다() {
            Host anotherHost = hostRepository.save(Host_생성("1234", 2345L));

            assertThatThrownBy(() -> spaceService.findSpace(anotherHost.getId(), space1.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 공간입니다.");
        }

        @Test
        void 존재하지_않는_HostId로_단일_Space를_조회할_경우_예외가_발생한다() {
            assertThatThrownBy(() -> spaceService.findSpace(0L, space1.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }

        @Test
        void 존재하지_않는_SpaceId로_단일_Space를_조회할_경우_예외가_발생한다() {
            assertThatThrownBy(() -> spaceService.findSpace(host.getId(), 0L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 공간입니다.");
        }
    }

    @Test
    void Space를_삭제하면_관련된_Job_Section_Task_RunningTask를_함께_삭제한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space = spaceRepository.save(Space_생성(host, "잠실 캠퍼스"));
        Job job = jobRepository.save(Job_생성(space, "청소"));
        Section section = sectionRepository.save(Section_생성(job, "대강의실"));
        Task task = taskRepository.save(Task_생성(section, "책상 닦기"));
        RunningTask runningTask = runningTaskRepository.save(RunningTask_생성(task.getId(), false));

        spaceService.removeSpace(host.getId(), space.getId());

        entityManager.flush();
        entityManager.clear();
        assertAll(
                () -> assertThat(spaceRepository.findById(space.getId())).isEmpty(),
                () -> assertThat(jobRepository.findById(job.getId())).isEmpty(),
                () -> assertThat(sectionRepository.findById(section.getId())).isEmpty(),
                () -> assertThat(taskRepository.findById(task.getId())).isEmpty(),
                () -> assertThat(runningTaskRepository.findById(runningTask.getTaskId())).isEmpty()
        );
    }
}
