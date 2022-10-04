package com.woowacourse.gongcheck.core.presentation.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SectionCreateRequest {

    @NotBlank(message = "SectionCreateRequest의 name은 비어있을 수 없습니다.")
    private String name;

    private String description;

    private String imageUrl;

    @Valid
    @NotEmpty
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
