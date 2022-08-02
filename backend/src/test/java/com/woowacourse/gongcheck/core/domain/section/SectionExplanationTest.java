package com.woowacourse.gongcheck.core.domain.section;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.Test;

class SectionExplanationTest {

    @Test
    void description이_제한길이_이상_들어오는_경우_예외가_발생한다() {
        String largeDescription = "123456789012345678901234567890123";
        assertThatThrownBy(() -> new SectionExplanation(largeDescription, null))
                .isInstanceOf(BusinessException.class)
                .hasMessage("section의 설명은 32자 이하여야합니다.");
    }

    @Test
    void description과_imageUrl이_null이_들어오는_경우_정상적으로_생성한다() {
        assertDoesNotThrow(() -> new SectionExplanation(null, null));
    }
}
