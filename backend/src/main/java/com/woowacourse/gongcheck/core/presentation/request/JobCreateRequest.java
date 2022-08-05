package com.woowacourse.gongcheck.core.presentation.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class JobCreateRequest {

    @NotNull(message = "이름은 null일 수 없습니다.")
    private String name;

    @Valid
    private List<SectionCreateRequest> sections;

    private JobCreateRequest() {
    }

    public JobCreateRequest(final String name, final List<SectionCreateRequest> sections) {
        this.name = name;
        this.sections = sections;
    }
}
