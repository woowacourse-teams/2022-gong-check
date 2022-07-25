package com.woowacourse.gongcheck.core.presentation.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SectionCreateRequest {

    @Size(min = 1, max = 20, message = "Section 이름은 한글자 이상 20자 이하여야 합니다.")
    @NotNull(message = "작업의 이름은 null 일 수 없습니다.")
    private String name;

    @Valid
    private List<TaskCreateRequest> tasks;

    private SectionCreateRequest() {
    }

    public SectionCreateRequest(final String name, final List<TaskCreateRequest> tasks) {
        this.name = name;
        this.tasks = tasks;
    }
}
