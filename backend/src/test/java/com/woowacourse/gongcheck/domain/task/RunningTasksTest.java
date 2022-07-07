package com.woowacourse.gongcheck.domain.task;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.gongcheck.exception.BusinessException;
import java.util.List;
import org.junit.jupiter.api.Test;

class RunningTasksTest {

    @Test
    void 모든_테스크가_완료되었는지_확인한다() {
        RunningTask runningTask1 = RunningTask_생성(1L, true);
        RunningTask runningTask2 = RunningTask_생성(2L, true);
        RunningTasks runningTasks = new RunningTasks(List.of(runningTask1, runningTask2));

        assertDoesNotThrow(runningTasks::validateCompletion);
    }

    @Test
    void 테스크가_하나라도_미완료상태인지_확인한다() {
        RunningTask runningTask1 = RunningTask_생성(1L, true);
        RunningTask runningTask2 = RunningTask_생성(2L, false);
        RunningTasks runningTasks = new RunningTasks(List.of(runningTask1, runningTask2));

        assertThatThrownBy(runningTasks::validateCompletion)
                .isInstanceOf(BusinessException.class)
                .hasMessage("모든 작업이 완료되지않아 제출이 불가합니다.");
    }
}
