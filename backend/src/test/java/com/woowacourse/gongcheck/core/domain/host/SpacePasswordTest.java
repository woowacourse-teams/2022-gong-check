package com.woowacourse.gongcheck.core.domain.host;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SpacePasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"0000", "1234", "9999"})
    void 비밀번호는_네자리_숫자로_이루어져야_한다(final String password) {
        assertThatCode(() -> new SpacePassword(password))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"가나다라", "가123", "123", "12345", "*&^%"})
    void 네자리_숫자가_아니면_예외가_발생한다(final String password) {
        assertThatThrownBy(() -> new SpacePassword(password))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("비밀번호는 네 자리 숫자로 이루어져야 합니다.");
    }

    @Test
    void 비밀번호_값이_같으면_같은_비밀번호이다() {
        assertThat(new SpacePassword("1234")).isEqualTo(new SpacePassword("1234"));
    }

    @Test
    void 비밀번호_값이_다르면_다른_비밀번호이다() {
        assertThat(new SpacePassword("1234")).isNotEqualTo(new SpacePassword("4567"));
    }
}
