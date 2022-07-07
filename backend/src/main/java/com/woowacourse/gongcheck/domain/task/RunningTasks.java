package com.woowacourse.gongcheck.domain.task;

import com.woowacourse.gongcheck.exception.BusinessException;
import java.util.List;

public class RunningTasks {

    private final List<RunningTask> runningTasks;

    public RunningTasks(final List<RunningTask> runningTasks) {
        this.runningTasks = runningTasks;
    }

    public void validateCompletion() {
        if (!isAllChecked()) {
            throw new BusinessException("모든 작업이 완료되지않아 제출이 불가합니다.");
        }
    }

    private boolean isAllChecked() {
        return runningTasks.stream()
                .allMatch(RunningTask::isChecked);
    }
}
