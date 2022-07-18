package com.woowacourse.gongcheck.presentation.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TaskCreateRequest {

    @Size(min = 1, max = 50, message = "Task 이름은 한글자 이상 50자 이하여야 합니다.")
    @NotNull(message = "작업의 이름은 null 일 수 없습니다.")
    private String name;

    private TaskCreateRequest() {
    }

    public TaskCreateRequest(final String name) {
        this.name = name;
    }
}
