package com.woowacourse.gongcheck.domain.host;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.gongcheck.exception.UnauthorizedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HostTest {

    @Nested
    class 비밀번호를_검사할_때 {

        private final Host host = Host.builder()
                .spacePassword("0123")
                .build();

        @Test
        void 일치하지_않으면_예외가_발생한다() {
            assertThatThrownBy(() -> host.checkPassword("1234"))
                    .isInstanceOf(UnauthorizedException.class)
                    .hasMessage("공간 비밀번호와 입력하신 비밀번호가 일치하지 않습니다.");
        }

        @Test
        void 일치하면_예외가_발생하지_않는다() {
            assertDoesNotThrow(() -> host.checkPassword("0123"));
        }
    }

    @Test
    void SpacePassword를_수정한다() {
        Host host = Host.builder()
                .spacePassword("1234")
                .build();
        String changedPassword = "4567";

        host.changeSpacePassword(changedPassword);

        assertThat(host.getSpacePassword()).isEqualTo(changedPassword);
    }
}
