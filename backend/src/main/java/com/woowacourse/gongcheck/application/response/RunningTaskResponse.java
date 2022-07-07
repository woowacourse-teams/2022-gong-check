package com.woowacourse.gongcheck.application.response;

import com.woowacourse.gongcheck.domain.task.Task;
import lombok.Getter;

@Getter
public class RunningTaskResponse {

    private Long id;
    private String name;
    private boolean checked;

    private RunningTaskResponse() {
    }

    private RunningTaskResponse(final Long id, final String name, final Boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public static RunningTaskResponse from(final Task task) {
        return new RunningTaskResponse(task.getId(), task.getName(), task.getRunningTask().isChecked());
    }
}
