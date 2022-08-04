package com.woowacourse.gongcheck.core.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    void 이름은_10자_이하여야_한다() {
        assertThatThrownBy(() -> new Name("12345678901"))
                .isInstanceOf(BusinessException.class)
                .hasMessage("이름은 10자 이하여야합니다.");
    }

    @Test
    void 이름은_빈_값일_수_없다() {
        assertThatThrownBy(() -> new Name(""))
                .isInstanceOf(BusinessException.class)
                .hasMessage("이름은 공백일 수 없습니다.");
    }
}
