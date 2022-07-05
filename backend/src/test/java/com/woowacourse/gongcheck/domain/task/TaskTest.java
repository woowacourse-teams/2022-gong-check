package com.woowacourse.gongcheck.domain.task;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void RunningTask를_생성한다() {
        Task task = Task.builder()
                .id(1L)
                .name("책상 청소")
                .build();

        assertThat(task.createRunningTask().getTaskId()).isEqualTo(1L);
    }
}
