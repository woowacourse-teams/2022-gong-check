package com.woowacourse.gongcheck.core.presentation.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class JobCreateRequest {

    @NotNull(message = "JobCreateRequest의 name은 null일 수 없습니다.")
    private String name;

    @Valid
    @NotEmpty(message = "sections 값은 비어있을 수 없습니다.")
    private List<SectionCreateRequest> sections;

    private JobCreateRequest() {
    }

    public JobCreateRequest(final String name, final List<SectionCreateRequest> sections) {
        this.name = name;
        this.sections = sections;
    }
}
