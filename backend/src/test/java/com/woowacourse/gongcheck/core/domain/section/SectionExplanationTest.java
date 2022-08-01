package com.woowacourse.gongcheck.core.domain.section;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SectionExplanationTest {

    @Test
    void description이_제한길이_이상_들어오는_경우_예외가_발생한다() {
        String largeDescription = "123456789012345678901234567890123";
        assertThatThrownBy(() -> new SectionExplanation(largeDescription, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("section의 설명은 32자 이하여야합니다.");
    }
}
