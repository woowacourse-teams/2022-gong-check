package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SubmissionRequest {

    @Size(min = 1, max = 20, message = "제출자 이름의 길이가 올바르지 않습니다.")
    @NotNull(message = "제출자 이름은 null 일 수 없습니다.")
    private String author;

    private SubmissionRequest() {
    }

    public SubmissionRequest(final String author) {
        this.author = author;
    }
}
