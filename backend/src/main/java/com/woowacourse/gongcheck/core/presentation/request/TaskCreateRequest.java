package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TaskCreateRequest {

    @NotNull(message = "TaskCreateRequest의 name은 null일 수 없습니다.")
    private String name;

    private String description;

    private String imageUrl;

    private TaskCreateRequest() {
    }

    public TaskCreateRequest(final String name, final String description, final String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
