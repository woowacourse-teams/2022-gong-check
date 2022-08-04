package com.woowacourse.gongcheck.core.application.response;

import com.woowacourse.gongcheck.core.domain.task.Task;
import lombok.Getter;

@Getter
public class RunningTaskResponse {

    private Long id;
    private String name;
    private boolean checked;
    private String imageUrl;
    private String description;

    private RunningTaskResponse() {
    }

    private RunningTaskResponse(final Long id, final String name, final boolean checked, final String imageUrl,
                                final String description) {
        this.id = id;
        this.name = name;
        this.checked = checked;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public static RunningTaskResponse from(final Task task) {
        return new RunningTaskResponse(task.getId(), task.getName().getValue(), task.getRunningTask().isChecked(),
                task.getImageUrl(), task.getDescription());
    }
}
