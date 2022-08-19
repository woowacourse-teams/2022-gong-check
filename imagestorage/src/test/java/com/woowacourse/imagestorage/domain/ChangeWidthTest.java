package com.woowacourse.imagestorage.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.imagestorage.exception.BusinessException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ChangeWidthTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 9})
    void 입력된_width가_최소길이보다_작으면_예외가_발생한다(final int input) {
        assertThatThrownBy(() -> new ChangeWidth(input))
                .isInstanceOf(BusinessException.class)
                .hasMessage("변경할 가로 길이가 작습니다.");
    }
}
