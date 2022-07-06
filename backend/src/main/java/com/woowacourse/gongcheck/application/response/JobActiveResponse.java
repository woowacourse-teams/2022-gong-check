package com.woowacourse.gongcheck.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JobActiveResponse {

    @JsonProperty(value = "isActive")
    private boolean isActive;

    private JobActiveResponse() {
    }

    private JobActiveResponse(boolean isActive) {
        this.isActive = isActive;
    }

    public static JobActiveResponse from(boolean isActive) {
        return new JobActiveResponse(isActive);
    }

    public boolean getIsActive() {
        return isActive;
    }
}
