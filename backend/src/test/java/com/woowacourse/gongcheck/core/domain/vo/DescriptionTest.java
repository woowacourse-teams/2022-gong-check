package com.woowacourse.gongcheck.core.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.Test;

class DescriptionTest {

    @Test
    void 설명은_128자를_초과할_수_없다() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            sb.append(i);
        }
        String value = sb.toString();
        assertThatThrownBy(() -> new Description(value))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("설명은 128자 이하여야 합니다.");
    }

    @Test
    void 설명값이_같으면_같은_객체이다() {
        Description description1 = new Description("이것은 설명");
        Description description2 = new Description("이것은 설명");

        assertThat(description1).isEqualTo(description2);
    }

    @Test
    void 설명값이_다르면_다른_객체이다() {
        Description description1 = new Description("이것은 설명");
        Description description2 = new Description("이것은 다른 설명");

        assertThat(description1).isNotEqualTo(description2);
    }
}
