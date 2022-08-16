package com.woowacourse.gongcheck.core.domain.task;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RunningTaskTest {

    @ParameterizedTest
    @CsvSource(value = {"false:true", "true:false"}, delimiter = ':')
    void RunningTask의_체크상태를_변경한다(final boolean isChecked, final boolean expected) {
        RunningTask runningTask = RunningTask.builder()
                .isChecked(isChecked)
                .build();

        runningTask.flipCheckedStatus();

        assertThat(runningTask.isChecked()).isEqualTo(expected);
    }

    @Test
    void RunningTask를_체크한다() {
        RunningTask runningTask = RunningTask.builder()
                .isChecked(false)
                .build();

        runningTask.check();

        assertThat(runningTask.isChecked()).isTrue();
    }
}
