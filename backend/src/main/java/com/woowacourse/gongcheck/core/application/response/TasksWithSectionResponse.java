package com.woowacourse.gongcheck.core.application.response;

import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.Tasks;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TasksWithSectionResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private List<TaskResponse> tasks;

    private TasksWithSectionResponse() {
    }

    private TasksWithSectionResponse(final Long id, final String name, final String imageUrl, final String description,
                                     final List<TaskResponse> tasks) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.tasks = tasks;
    }

    public static List<TasksWithSectionResponse> from(final Tasks tasks) {
        Map<Section, List<Task>> map = tasks.getTasks()
                .stream()
                .collect(Collectors.groupingBy(Task::getSection));

        return map.entrySet()
                .stream()
                .map(entry -> TasksWithSectionResponse.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private static TasksWithSectionResponse of(final Section section, final List<Task> tasks) {
        return new TasksWithSectionResponse(section.getId(), section.getName(), section.getImageUrl(),
                section.getDescription(), tasks.stream()
                .map(TaskResponse::from)
                .collect(Collectors.toList()));
    }
}
