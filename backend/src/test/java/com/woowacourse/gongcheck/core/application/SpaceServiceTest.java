package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.ApplicationTest;
import com.woowacourse.gongcheck.SupportRepository;
import com.woowacourse.gongcheck.core.application.response.SpaceResponse;
import com.woowacourse.gongcheck.core.application.response.SpacesResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.presentation.request.SpaceCreateRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("SpaceService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SpaceServiceTest {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private SupportRepository repository;

    @Nested
    class findSpaces_메서드는 {

        @Nested
        class 존재하지_않는_Host_id를_입력받는_경우 {

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> spaceService.findSpaces(0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하는_Host의_id를_입력받은_경우 {

            private Host host;
            private SpacesResponse expected;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));

                Space space_1 = Space_생성(host, "잠실 캠퍼스");
                Space space_2 = Space_생성(host, "선릉 캠퍼스");
                Space space_3 = Space_생성(host, "양평같은방");
                List<Space> spaces = repository.saveAll(List.of(space_1, space_2, space_3));

                expected = SpacesResponse.from(spaces);
            }

            @Test
            void 해당_Host가_소유한_Space를_응답으로_반환한다() {
                SpacesResponse actual = spaceService.findSpaces(host.getId());

                assertThat(actual.getSpaces())
                        .usingRecursiveFieldByFieldElementComparator()
                        .isEqualTo(expected.getSpaces());
            }
        }
    }

    @Nested
    class createSpace_메서드는 {

        @Nested
        class Host가_입력받은_Space_이름과_같은_Space를_이미_가지고_있는_경우 {

            private Host host;
            private SpaceCreateRequest request;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                Space space = repository.save(Space_생성(host, "잠실 캠퍼스"));
                request = new SpaceCreateRequest(space.getName().getValue(), "https://image.gongcheck.shop/123sdf5");
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> spaceService.createSpace(host.getId(), request))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("이미 존재하는 이름입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Host_id를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private SpaceCreateRequest request;

            @BeforeEach
            void setUp() {
                request = new SpaceCreateRequest("이것은 유일한 Space이름", "https://image.gongcheck.shop/123sdf5");
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> spaceService.createSpace(NON_EXIST_HOST_ID, request))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 입력받은_Host가_존재하는_경우 {

            private static final String SPACE_NAME = "잠실 캠퍼스";
            private static final String SPACE_IMAGE_URL = "https://image.gongcheck.shop/123sdf5";

            private Host host;
            private SpaceCreateRequest request;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                request = new SpaceCreateRequest(SPACE_NAME, SPACE_IMAGE_URL);
            }

            @Test
            void Space를_생성한다() {
                Long spaceId = spaceService.createSpace(host.getId(), request);
                Space actual = repository.getById(Space.class, spaceId);

                assertAll(
                        () -> assertThat(actual.getName().getValue()).isEqualTo(SPACE_NAME),
                        () -> assertThat(actual.getImageUrl()).isEqualTo(SPACE_IMAGE_URL)
                );
            }
        }
    }

    @Nested
    class findSpace_메서드는 {

        @Nested
        class Space_목록이_존재하는_경우 {

            private static final String SPACE_NAME = "잠실 캠퍼스";

            private Host host;
            private Space space;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 2345L));
                space = repository.save(Space_생성(host, SPACE_NAME));
            }

            @Test
            void Job_목록을_조회한다() {
                SpaceResponse actual = spaceService.findSpace(host.getId(), space.getId());

                assertAll(
                        () -> assertThat(actual.getId()).isEqualTo(space.getId()),
                        () -> assertThat(actual.getName()).isEqualTo(SPACE_NAME)
                );
            }
        }

        @Nested
        class 입력받은_Host가_입력받은_Space를_가지고_있지_않은_경우 {

            private Space space;
            private Host anotherHost;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                space = repository.save(Space_생성(host, "잠실 캠퍼스"));
                anotherHost = repository.save(Host_생성("1234", 2345L));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> spaceService.findSpace(anotherHost.getId(), space.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 공간입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Host_id를_입력받은_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private Space space;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                space = repository.save(Space_생성(host, "잠실 캠퍼스"));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> spaceService.findSpace(NON_EXIST_HOST_ID, space.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 존재하지_않는_Space_id를_입력받은_경우 {

            private Host host;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> spaceService.findSpace(host.getId(), 0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 공간입니다.");
            }
        }

        @Nested
        class 입력받은_Host가_입력받은_Space를_소유하고_있는_경우 {

            private static final String SPACE_NAME = "잠실 캠퍼스";

            private Host host;
            private Space space;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                space = repository.save(Space_생성(host, SPACE_NAME));
            }

            @Test
            void Space_응답을_반환한다() {
                SpaceResponse actual = spaceService.findSpace(host.getId(), space.getId());

                assertAll(
                        () -> assertThat(actual.getId()).isEqualTo(space.getId()),
                        () -> assertThat(actual.getName()).isEqualTo(SPACE_NAME)
                );
            }
        }
    }

    @Nested
    class removeSpace_메서드는 {

        @Nested
        class 입력받은_Host_id가_존재하지_않는_경우 {

            private static final long NON_EXIST_HOST_ID = 0L;

            private Space space;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                space = repository.save(Space_생성(host, "잠실 캠퍼스"));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> spaceService.removeSpace(NON_EXIST_HOST_ID, space.getId()))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 입력받은_Host가_입력받은_Space를_소유하고_있지_않은_경우 {

            private Host anotherHost;
            private Space space;

            @BeforeEach
            void setUp() {
                Host host = repository.save(Host_생성("1234", 1234L));
                anotherHost = repository.save(Host_생성("1234", 4567L));
                space = repository.save(Space_생성(host, "잠실 캠퍼스"));
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> spaceService.removeSpace(anotherHost.getId(), space.getId()))
                        .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        class 입력받은_Space_id가_존재하면 {

            private Host host;
            private Space space;
            private Job job;
            private Section section;
            private Task task;
            private RunningTask runningTask;

            @BeforeEach
            void setUp() {
                host = repository.save(Host_생성("1234", 1234L));
                space = repository.save(Space_생성(host, "잠실 캠퍼스"));
                job = repository.save(Job_생성(space, "청소"));
                section = repository.save(Section_생성(job, "대강의실"));
                task = repository.save(Task_생성(section, "책상 닦기"));
                runningTask = repository.save(RunningTask_생성(task.getId(), false));
            }

            @Test
            void 해당_Space_및_관련된_Job_Section_Task_RunningTask를_삭제한다() {
                spaceService.removeSpace(host.getId(), space.getId());

                assertAll(
                        () -> assertThat(repository.findById(Space.class, space.getId())).isEmpty(),
                        () -> assertThat(repository.findById(Job.class, job.getId())).isEmpty(),
                        () -> assertThat(repository.findById(Section.class, section.getId())).isEmpty(),
                        () -> assertThat(repository.findById(Task.class, task.getId())).isEmpty(),
                        () -> assertThat(repository.findById(RunningTask.class, runningTask.getTaskId())).isEmpty()
                );
            }
        }
    }
}
