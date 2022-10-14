package com.woowacourse.gongcheck.core.presentation.request;

import lombok.Getter;

@Getter
public class FlipRequest {

    private Long taskId;

    private FlipRequest() {
    }

    public FlipRequest(Long taskId) {
        this.taskId = taskId;
    }
}
