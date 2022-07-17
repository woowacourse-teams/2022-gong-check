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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
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
    class 공간_조회 {

        @Test
        void 공간을_조회한다() {
            Host host = hostRepository.save(Host_생성("1234"));
            Space space1 = Space_생성(host, "잠실");
            Space space2 = Space_생성(host, "선릉");
            Space space3 = Space_생성(host, "양평같은방");
            spaceRepository.saveAll(List.of(space1, space2, space3));

            SpacesResponse result = spaceService.findPage(host.getId(), PageRequest.of(0, 2));

            assertAll(
                    () -> assertThat(result.getSpaces()).hasSize(2),
                    () -> assertThat(result.isHasNext()).isTrue()
            );
        }

        @Test
        void 존재하지_않는_호스트로_공간을_조회할_경우_예외를_던진다() {
            assertThatThrownBy(() -> spaceService.findPage(0L, PageRequest.of(0, 1)))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }
    }

    @Nested
    class 공간_생성 {

        @Test
        void 공간을_생성한다() {
            Host host = hostRepository.save(Host_생성("1234"));
            SpaceCreateRequest spaceCreateRequest = new SpaceCreateRequest("잠실 캠퍼스",
                    new MockMultipartFile("잠실 캠퍼스 사진", new byte[]{}));
            Long spaceId = spaceService.createSpace(host.getId(), spaceCreateRequest);

            assertThat(spaceId).isNotNull();
        }

        @Test
        void 이미_존재하는_공간_이름을_입력할_경우_예외가_발생한다() {
            Host host = hostRepository.save(Host_생성("1234"));
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
        void 존재하지_않는_호스트로_생성하려는_경우_예외가_발생한다() {
            SpaceCreateRequest request = new SpaceCreateRequest("잠실 캠퍼스",
                    new MockMultipartFile("잠실 캠퍼스 사진", new byte[]{}));

            assertThatThrownBy(() -> spaceService.createSpace(0L, request))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }
    }

    @Test
    void Space를_삭제하면_관련된_Job_Section_Task를_함께_삭제한다() {
        Host host = hostRepository.save(Host_생성("1234"));
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
