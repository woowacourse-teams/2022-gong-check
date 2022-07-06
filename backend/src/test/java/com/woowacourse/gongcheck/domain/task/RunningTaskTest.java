package com.woowacourse.gongcheck.domain.task;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RunningTaskTest {

    @ParameterizedTest
    @CsvSource(value = {"false:true", "true:false"}, delimiter = ':')
    void RunningTask의_체크_상태를_변경한다(boolean isChecked, boolean expected) {
        RunningTask runningTask = RunningTask.builder()
                .isChecked(isChecked)
                .build();

        runningTask.changeCheckedStatus();

        assertThat(runningTask.isChecked()).isEqualTo(expected);
    }
}
