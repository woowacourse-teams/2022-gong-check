package com.woowacourse.gongcheck.core.domain.task;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TaskExplanationTest {

    @Test
    void description이_제한길이_이상_들어오는_경우_예외가_발생한다() {
        String largeDescription = "123456789012345678901234567890123";
        assertThatThrownBy(() -> new TaskExplanation(largeDescription, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("task의 설명은 32자 이하여야합니다.");
    }
}
