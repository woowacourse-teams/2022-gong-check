package com.woowacourse.gongcheck.auth.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GuestEnterRequest {

    @NotNull
    private String password;

    private GuestEnterRequest() {
    }

    public GuestEnterRequest(final String password) {
        this.password = password;
    }
}
