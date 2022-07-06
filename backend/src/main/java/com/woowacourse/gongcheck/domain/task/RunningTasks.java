package com.woowacourse.gongcheck.domain.task;

import java.util.List;

public class RunningTasks {

    private final List<RunningTask> runningTasks;

    public RunningTasks(final List<RunningTask> runningTasks) {
        this.runningTasks = runningTasks;
    }

    public boolean isAllChecked() {
        return runningTasks.stream()
                .filter(runningTask -> !runningTask.isChecked())
                .findAny()
                .isEmpty();
    }
}
