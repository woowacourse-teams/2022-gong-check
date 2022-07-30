package com.woowacourse.gongcheck.core.domain.task;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class TasksTest {

    @Test
    void Tasks_가_비어있다면_true를_반환한다() {
        Tasks tasks = new Tasks(List.of());

        assertThat(tasks.isEmpty()).isTrue();
    }

    @Test
    void Tasks_가_비어있다면_false를_반환한다() {
        Task task = Task.builder()
                .build();
        Tasks tasks = new Tasks(List.of(task));

        assertThat(tasks.isEmpty()).isFalse();
    }
}
