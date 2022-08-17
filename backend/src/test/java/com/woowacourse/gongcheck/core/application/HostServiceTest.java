package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.ApplicationTest;
import com.woowacourse.gongcheck.SupportRepository;
import com.woowacourse.gongcheck.auth.application.EntranceCodeProvider;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.presentation.request.SpacePasswordChangeRequest;
import com.woowacourse.gongcheck.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("HostService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HostServiceTest {

    @Autowired
    private HostService hostService;

    @Autowired
    private SupportRepository repository;

    @Autowired
    private EntranceCodeProvider entranceCodeProvider;

    abstract class Host가_존재한다면 {
        protected final Host host = repository.save(Host_생성("1234", 1L));
    }

    @Nested
    class changeSpacePassword_메소드는 {

        @Nested
        class 존재하는_HostId와_수정할_패스워드를_입력받는다면 extends Host가_존재한다면 {

            private static final String CHANGING_PASSWORD = "4567";

            private final SpacePasswordChangeRequest spacePasswordChangeRequest = new SpacePasswordChangeRequest(
                    CHANGING_PASSWORD);

            @Test
            void 패스워드를_수정한다() {
                hostService.changeSpacePassword(host.getId(), spacePasswordChangeRequest);
                Host actual = repository.getById(Host.class, host.getId());

                assertThat(actual.getSpacePassword().getValue()).isEqualTo(CHANGING_PASSWORD);
            }
        }

        @Nested
        class 존재하지_않는_HostId를_입력받는다면 {

            private static final String CHANGING_PASSWORD = "4567";
            private static final long NON_EXIST_HOST_ID = 0L;

            private final SpacePasswordChangeRequest spacePasswordChangeRequest = new SpacePasswordChangeRequest(
                    CHANGING_PASSWORD);

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> hostService.changeSpacePassword(NON_EXIST_HOST_ID, spacePasswordChangeRequest))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }
    }

    @Nested
    class createEntranceCode_메소드는 {

        @Nested
        class 존재하는_HostId를_입력받는다면 extends Host가_존재한다면 {

            private final Long hostId = host.getId();
            private final String expected = entranceCodeProvider.createEntranceCode(hostId);

            @Test
            void entranceCode를_반환한다() {
                String actual = hostService.createEntranceCode(hostId);
                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 존재하지_않는_HostId를_입력받는다면 {

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> hostService.createEntranceCode(1L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessageContaining("존재하지 않는 호스트입니다.");
            }
        }
    }
}
