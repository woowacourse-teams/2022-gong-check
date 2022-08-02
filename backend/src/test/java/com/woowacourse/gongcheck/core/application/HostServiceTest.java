package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.auth.application.EnterCodeProvider;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
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

    @Autowired
    private EnterCodeProvider enterCodeProvider;

    @Nested
    class changeSpacePassword_메소드는 {

        @Nested
        class 존재하는_Host의_id와_수정할_패스워드를_받는_경우 {

            private static final String ORIGIN_PASSWORD = "1234";
            private static final String CHANGING_PASSWORD = "4567";
            private static final long GITHUB_ID = 1234L;

            private SpacePasswordChangeRequest spacePasswordChangeRequest;
            private Long hostId;

            @BeforeEach
            void setUp() {
                spacePasswordChangeRequest = new SpacePasswordChangeRequest(CHANGING_PASSWORD);
                hostId = hostRepository.save(Host_생성(ORIGIN_PASSWORD, GITHUB_ID))
                        .getId();
            }

            @Test
            void 패스워드를_수정한다() {
                hostService.changeSpacePassword(hostId, spacePasswordChangeRequest);
                Host actual = hostRepository.getById(hostId);

                assertAll(
                        () -> assertThat(actual.getSpacePassword().getValue()).isEqualTo(CHANGING_PASSWORD),
                        () -> assertThat(actual.getGithubId()).isEqualTo(GITHUB_ID),
                        () -> assertThat(actual.getId()).isEqualTo(hostId)
                );
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
    class createEnterCode_메소드는 {

        @Nested
        class 존재하는_Host의_id를_받는_경우 {

            private Long hostId;
            private String expected;

            @BeforeEach
            void setUp() {
                hostId = hostRepository.save(Host_생성("1234", 1111L))
                        .getId();
                expected = enterCodeProvider.createEnterCode(hostId);
            }

            @Test
            void 입장코드를_반환한다() {
                String actual = hostService.createEnterCode(hostId);
                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 존재하지_않는_Host의_id를_받는_경우 {

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> hostService.createEnterCode(0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }
    }
}
