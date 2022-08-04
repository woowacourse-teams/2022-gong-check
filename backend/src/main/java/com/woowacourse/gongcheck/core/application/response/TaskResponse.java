package com.woowacourse.gongcheck.core.application.response;

import com.woowacourse.gongcheck.core.domain.task.Task;
import lombok.Getter;

@Getter
public class TaskResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;

    private TaskResponse() {
    }

    private TaskResponse(final Long id, final String name, final String imageUrl, final String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public static TaskResponse from(final Task task) {
        return new TaskResponse(task.getId(), task.getName().getValue(), task.getImageUrl(), task.getDescription());
    }
}
