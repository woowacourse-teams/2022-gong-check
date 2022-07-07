package com.woowacourse.gongcheck.domain.task;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Tasks {

    private final List<Task> tasks;

    public Tasks(final List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<RunningTask> createRunningTasks() {
        return tasks.stream()
                .map(Task::createRunningTask)
                .collect(Collectors.toList());
    }

    public List<Long> getTaskIds() {
        return tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Tasks tasks1 = (Tasks) o;
        return Objects.equals(tasks, tasks1.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks);
    }
}
