package com.woowacourse.gongcheck.auth.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.auth.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
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
@DisplayName("GuestAuthService 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GuestAuthServiceTest {

    @Autowired
    private GuestAuthService guestAuthService;

    @Autowired
    private HostRepository hostRepository;

    @Nested
    class createToken_메소드는 {

        private static final String CORRECT_PASSWORD = "1234";

        @Nested
        class 존재하는_Host의_id와_정확한_비밀번호를_입력하는_경우 {

            private Long hostId;
            private GuestEnterRequest guestEnterRequest;

            @BeforeEach
            void setUp() {
                hostId = hostRepository.save(Host_생성(CORRECT_PASSWORD, 1234L))
                        .getId();
                guestEnterRequest = new GuestEnterRequest(CORRECT_PASSWORD);
            }

            @Test
            void Space_사용을_위한_토큰을_발행한다() {
                GuestTokenResponse token = guestAuthService.createToken(hostId, guestEnterRequest);

                assertThat(token.getToken()).isNotNull();
            }
        }

        @Nested
        class 존재하지_않는_Host의_id를_받는_경우 {

            private GuestEnterRequest guestEnterRequest;

            @BeforeEach
            void setUp() {
                guestEnterRequest = new GuestEnterRequest(CORRECT_PASSWORD);
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> guestAuthService.createToken(0L, guestEnterRequest))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }

        @Nested
        class 잘못된_비밀번호를_입력하는_경우 {

            private static final String ERROR_PASSWORD = "4567";

            private Long hostId;
            private GuestEnterRequest errorGuestEnterRequest;

            @BeforeEach
            void setUp() {
                hostId = hostRepository.save(Host_생성(CORRECT_PASSWORD, 1234L))
                        .getId();
                errorGuestEnterRequest = new GuestEnterRequest(ERROR_PASSWORD);
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> guestAuthService.createToken(hostId, errorGuestEnterRequest))
                        .isInstanceOf(UnauthorizedException.class)
                        .hasMessage("공간 비밀번호와 입력하신 비밀번호가 일치하지 않습니다.");
            }
        }
    }
}
