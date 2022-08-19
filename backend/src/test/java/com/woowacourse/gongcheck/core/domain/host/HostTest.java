package com.woowacourse.gongcheck.core.domain.host;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HostTest {

    @Test
    void Host_생성시_기본_비밀번호는_0000_이다() {
        Host host = Host.builder()
                .build();

        assertThat(host.getSpacePassword().getValue()).isEqualTo("0000");
    }

    @Nested
    class 비밀번호를_검사할_때 {

        private final Host host = Host_생성("0123", 1234L);

        @Test
        void 일치하지_않으면_예외가_발생한다() {
            SpacePassword spacePassword = new SpacePassword("1234");

            assertThatThrownBy(() -> host.checkPassword(spacePassword))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("공간 비밀번호와 입력하신 비밀번호가 일치하지 않습니다.");
        }

        @Test
        void 일치하면_예외가_발생하지_않는다() {
            SpacePassword spacePassword = new SpacePassword("0123");
            assertDoesNotThrow(() -> host.checkPassword(spacePassword));
        }
    }

    @Test
    void SpacePassword를_수정한다() {
        Host host = Host_생성("0123", 1234L);
        String changedPassword = "4567";

        host.changeSpacePassword(new SpacePassword(changedPassword));

        assertThat(host.getSpacePassword()).isEqualTo(new SpacePassword(changedPassword));
    }
}
