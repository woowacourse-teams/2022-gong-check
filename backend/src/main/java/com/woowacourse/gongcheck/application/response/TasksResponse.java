package com.woowacourse.gongcheck.application.response;

import com.woowacourse.gongcheck.domain.task.Tasks;
import java.util.List;
import lombok.Getter;

@Getter
public class TasksResponse {
    
    private List<TasksWithSectionResponse> sections;

    private TasksResponse() {
    }

    private TasksResponse(final List<TasksWithSectionResponse> sections) {
        this.sections = sections;
    }

    public static TasksResponse from(final Tasks tasks) {
        return new TasksResponse(TasksWithSectionResponse.from(tasks));
    }
}
