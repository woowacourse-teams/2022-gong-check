package com.woowacourse.gongcheck.presentation.request;

import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TaskRequest {

    @Size(min = 1, max = 50, message = "Task 이름은 한글자 이상 50자 이하여야 합니다.")
    private String name;

    private TaskRequest() {
    }

    public TaskRequest(final String name) {
        this.name = name;
    }
}
