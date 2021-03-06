package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.host.SpacePassword;
import com.woowacourse.gongcheck.core.presentation.request.SpacePasswordChangeRequest;
import com.woowacourse.gongcheck.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("HostService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HostServiceTest {

    @Autowired
    private HostService hostService;

    @Autowired
    private HostRepository hostRepository;

    @Nested
    class changeSpacePassword_메소드는 {

        @Nested
        class 존재하는_Host의_id와_수정할_패스워드를_받는_경우 {

            private static final String ORIGIN_PASSWORD = "1234";
            private static final String CHANGING_PASSWORD = "4567";

            private SpacePasswordChangeRequest spacePasswordChangeRequest;
            private Long hostId;

            @BeforeEach
            void setUp() {
                spacePasswordChangeRequest = new SpacePasswordChangeRequest(CHANGING_PASSWORD);
                hostId = hostRepository.save(Host_생성(ORIGIN_PASSWORD, 1234L))
                        .getId();
            }

            @Test
            void 패스워드를_수정한다() {
                hostService.changeSpacePassword(hostId, spacePasswordChangeRequest);
                SpacePassword actual = hostRepository.getById(hostId)
                        .getSpacePassword();

                assertThat(actual.getValue()).isEqualTo(CHANGING_PASSWORD);
            }
        }

        @Nested
        class 존재하지_않는_Host의_id를_받는_경우 {

            private static final String CHANGING_PASSWORD = "4567";

            private SpacePasswordChangeRequest spacePasswordChangeRequest;
            private Long hostId;

            @BeforeEach
            void setUp() {
                spacePasswordChangeRequest = new SpacePasswordChangeRequest(CHANGING_PASSWORD);
                hostId = 0L;
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> hostService.changeSpacePassword(hostId, spacePasswordChangeRequest))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }
    }

    @Nested
    class getHostId_메소드는 {

        @Nested
        class 존재하는_Host의_id를_받는_경우 {

            private Long hostId;

            @BeforeEach
            void setUp() {
                hostId = hostRepository.save(Host_생성("1234", 1111L))
                        .getId();
            }

            @Test
            void Host의_id를_반환한다() {
                Long actual = hostService.getHostId(hostId);
                assertThat(actual).isEqualTo(hostId);
            }
        }

        @Nested
        class 존재하지_않는_Host의_id를_받는_경우 {

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> hostService.getHostId(0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }
    }
}
