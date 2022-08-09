package com.woowacourse.gongcheck.core.presentation.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SectionCreateRequest {

    @NotNull(message = "SectionCreateRequest 의 name은 null일 수 없습니다.")
    private String name;

    private String description;

    private String imageUrl;

    @Valid
    private List<TaskCreateRequest> tasks;

    private SectionCreateRequest() {
    }

    public SectionCreateRequest(final String name, final String description, final String imageUrl,
                                final List<TaskCreateRequest> tasks) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tasks = tasks;
    }
}
