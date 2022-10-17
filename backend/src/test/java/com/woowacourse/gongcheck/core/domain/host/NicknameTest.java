package com.woowacourse.gongcheck.core.domain.host;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.Test;

class NicknameTest {

    @Test
    void nickname이_공백이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Nickname(" "))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("nickname은 공백일 수 없습니다.");
    }
}
