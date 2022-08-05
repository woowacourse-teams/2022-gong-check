package com.woowacourse.gongcheck.core.domain.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void description과_imageUrl이_null이_들어오는_경우_정상적으로_생성한다() {
        assertDoesNotThrow(() -> Task.builder()
                .build());
    }
}
