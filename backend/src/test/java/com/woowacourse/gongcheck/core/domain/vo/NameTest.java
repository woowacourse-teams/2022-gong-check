package com.woowacourse.gongcheck.core.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    void 이름은_10자_이하여야_한다() {
        assertThatThrownBy(() -> new Name("12345678901"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("이름은 10자 이하여야합니다.");
    }

    @Test
    void 이름은_빈_값일_수_없다() {
        assertThatThrownBy(() -> new Name(""))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("이름은 공백일 수 없습니다.");
    }

    @Test
    void 이름_값이_같으면_같은_객체이다() {
        Name name1 = new Name("awesome");
        Name name2 = new Name("awesome");

        assertThat(name1).isEqualTo(name2);
    }

    @Test
    void 이름_값이_다르면_다른_객체이다() {
        Name name1 = new Name("awesome");
        Name name2 = new Name("notAwesome");

        assertThat(name1).isNotEqualTo(name2);
    }
}
