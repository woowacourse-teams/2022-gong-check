package com.woowacourse.gongcheck.core.application.support;

import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.Tasks;
import java.util.List;
import java.util.stream.Collectors;

public class MessageGenerator {

    private MessageGenerator() {
    }

    public static String taskIdsGenerate(final Tasks tasks) {
        return tasks.getTaskIds()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    public static String runningTasksGenerate(final List<RunningTask> runningTasks) {
        return runningTasks.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
