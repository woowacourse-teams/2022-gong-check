package com.woowacourse.gongcheck.core.domain.space;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    void Space_이름은_20자_이하여야_한다() {
        assertThatThrownBy(() -> new Name("123456789123467891234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공간의 이름은 20자 이하여야합니다.");
    }

    @Test
    void Space_이름은_빈_값일_수_없다() {
        assertThatThrownBy(() -> new Name(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공간의 이름은 공백일 수 없습니다.");
    }
}
