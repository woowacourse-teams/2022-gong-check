package com.woowacourse.gongcheck.application.response;

import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.task.Task;
import com.woowacourse.gongcheck.domain.task.Tasks;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class RunningTasksWithSectionResponse {

    private Long id;
    private String name;
    private List<RunningTaskResponse> tasks;

    private RunningTasksWithSectionResponse() {
    }

    private RunningTasksWithSectionResponse(final Long id, final String name,
                                            final List<RunningTaskResponse> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    public static List<RunningTasksWithSectionResponse> from(final Tasks tasks) {
        Map<Section, List<Task>> map = tasks.getTasks()
                .stream()
                .collect(Collectors.groupingBy(Task::getSection));

        return map.entrySet()
                .stream()
                .map(entry -> RunningTasksWithSectionResponse.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private static RunningTasksWithSectionResponse of(final Section section, final List<Task> tasks) {
        return new RunningTasksWithSectionResponse(section.getId(), section.getName(),
                tasks.stream()
                        .map(RunningTaskResponse::from)
                        .collect(Collectors.toList()));
    }
}
