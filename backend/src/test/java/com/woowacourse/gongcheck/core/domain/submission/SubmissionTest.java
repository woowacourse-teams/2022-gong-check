package com.woowacourse.gongcheck.core.domain.submission;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.Test;

class SubmissionTest {

    @Test
    void author의_이름은_10자_이하여야_한다() {
        assertThatThrownBy(() -> Submission.builder()
                .author("12345678901")
                .build())
                .isInstanceOf(BusinessException.class)
                .hasMessage("제출자 이름은 10자 이하여야 합니다.");
    }
}
