package com.woowacourse.gongcheck.application.response;

import lombok.Getter;

@Getter
public class JobActiveResponse {

    private boolean isActive;

    private JobActiveResponse() {
    }

    private JobActiveResponse(boolean isActive) {
        this.isActive = isActive;
    }

    public static JobActiveResponse from(boolean isActive) {
        return new JobActiveResponse(isActive);
    }
}
