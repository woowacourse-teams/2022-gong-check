package com.woowacourse.gongcheck.domain.task;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class RunningTasksTest {

    @Test
    void 모든_테스크가_완료되었는지_확인한다() {
        RunningTask runningTask1 = RunningTask.builder()
                .taskId(1L)
                .isChecked(true)
                .createdAt(LocalDateTime.now())
                .build();
        RunningTask runningTask2 = RunningTask.builder()
                .taskId(2L)
                .isChecked(true)
                .createdAt(LocalDateTime.now())
                .build();
        RunningTasks runningTasks = new RunningTasks(List.of(runningTask1, runningTask2));

        assertThat(runningTasks.isAllChecked()).isTrue();
    }

    @Test
    void 테스크가_하나라도_미완료상태인지_확인한다() {
        RunningTask runningTask1 = RunningTask.builder()
                .taskId(1L)
                .isChecked(true)
                .createdAt(LocalDateTime.now())
                .build();
        RunningTask runningTask2 = RunningTask.builder()
                .taskId(2L)
                .isChecked(false)
                .createdAt(LocalDateTime.now())
                .build();
        RunningTasks runningTasks = new RunningTasks(List.of(runningTask1, runningTask2));

        assertThat(runningTasks.isAllChecked()).isFalse();
    }
}
