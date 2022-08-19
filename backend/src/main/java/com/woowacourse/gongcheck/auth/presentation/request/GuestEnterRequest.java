package com.woowacourse.gongcheck.auth.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GuestEnterRequest {

    @NotNull(message = "GuestEnterRequest의 password는 null일 수 없습니다.")
    private String password;

    private GuestEnterRequest() {
    }

    public GuestEnterRequest(final String password) {
        this.password = password;
    }
}
