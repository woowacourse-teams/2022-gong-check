package com.woowacourse.gongcheck.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.gongcheck.exception.UnauthorizedException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Nested
    class 비밀번호를_검사할_때 {

        private final Member member = Member.builder()
                .spacePassword("0123")
                .build();

        @Test
        void 일치하지_않으면_예외가_발생한다() {
            assertThatThrownBy(() -> member.checkPassword("1234"))
                    .isInstanceOf(UnauthorizedException.class)
                    .hasMessage("공간 비밀번호와 입력하신 비밀번호가 일치하지 않습니다.");
        }

        @Test
        void 일치하면_예외가_발생하지_않는다() {
            assertDoesNotThrow(() -> member.checkPassword("0123"));
        }
    }
}
