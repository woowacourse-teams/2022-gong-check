package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SubmissionRequest {

    @NotNull(message = "SubmissionRequest의 author은 null 일 수 없습니다.")
    private String author;

    private SubmissionRequest() {
    }

    public SubmissionRequest(final String author) {
        this.author = author;
    }
}
