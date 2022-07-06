package com.woowacourse.gongcheck.application.response;

import com.woowacourse.gongcheck.domain.task.Tasks;
import java.util.List;
import lombok.Getter;

@Getter
public class RunningTasksResponse {

    private List<RunningTasksWithSectionResponse> sections;

    private RunningTasksResponse() {
    }

    private RunningTasksResponse(
            final List<RunningTasksWithSectionResponse> sections) {
        this.sections = sections;
    }

    public static RunningTasksResponse from(final Tasks tasks) {
        return new RunningTasksResponse(RunningTasksWithSectionResponse.from(tasks));
    }
}
