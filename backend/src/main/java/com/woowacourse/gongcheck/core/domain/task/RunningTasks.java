package com.woowacourse.gongcheck.core.domain.task;

import com.woowacourse.gongcheck.core.application.support.LoggingFormatConverter;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.List;

public class RunningTasks {

    private final List<RunningTask> runningTasks;

    public RunningTasks(final List<RunningTask> runningTasks) {
        this.runningTasks = runningTasks;
    }

    public void validateCompletion() {
        if (!isAllChecked()) {
            String message = String.format("모든 작업이 완료되지않아 제출이 불가합니다. runningTasksIds = %s",
                    LoggingFormatConverter.runningTasksConvert(runningTasks));
            throw new BusinessException(message, ErrorCode.R003);
        }
    }

    public void check() {
        for (RunningTask runningTask : runningTasks) {
            runningTask.check();
        }
    }

    private boolean isAllChecked() {
        return runningTasks.stream()
                .allMatch(RunningTask::isChecked);
    }
}
