package com.woowacourse.gongcheck.core.domain.section;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SectionDescriptionTest {

    @Test
    void announcement가_제한길이_이상_들어오는_경우_예외가_발생한다() {
        String largeAnnouncement = "123456789012345678901234567890123";
        assertThatThrownBy(() -> new SectionDescription(largeAnnouncement, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("section의 설명은 32자 이하여야합니다.");
    }
}
