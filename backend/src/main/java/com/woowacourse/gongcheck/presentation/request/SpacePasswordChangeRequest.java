package com.woowacourse.gongcheck.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SpacePasswordChangeRequest {

    @NotNull
    private String password;

    private SpacePasswordChangeRequest() {
    }

    public SpacePasswordChangeRequest(final String password) {
        this.password = password;
    }
}
