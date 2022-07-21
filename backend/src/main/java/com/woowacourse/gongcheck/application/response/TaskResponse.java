package com.woowacourse.gongcheck.application.response;

import com.woowacourse.gongcheck.domain.task.Task;
import lombok.Getter;

@Getter
public class TaskResponse {

    private Long id;
    private String name;

    private TaskResponse() {
    }

    private TaskResponse(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static TaskResponse from(final Task task) {
        return new TaskResponse(task.getId(), task.getName());
    }
}
