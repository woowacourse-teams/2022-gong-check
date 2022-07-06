package com.woowacourse.gongcheck.application.response;

import lombok.Getter;

@Getter
public class JobActiveResponse {

    private boolean active;

    private JobActiveResponse() {
    }

    private JobActiveResponse(final boolean active) {
        this.active = active;
    }

    public static JobActiveResponse from(final boolean active) {
        return new JobActiveResponse(active);
    }
}
