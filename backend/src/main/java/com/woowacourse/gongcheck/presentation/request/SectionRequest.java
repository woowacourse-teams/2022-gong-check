package com.woowacourse.gongcheck.presentation.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SectionRequest {

    @Size(min = 1, max = 20, message = "Section 이름은 한글자 이상 20자 이하여야 합니다.")
    private String name;

    @Valid
    private List<TaskRequest> tasks;

    private SectionRequest() {
    }

    public SectionRequest(final String name, final List<TaskRequest> tasks) {
        this.name = name;
        this.tasks = tasks;
    }
}
