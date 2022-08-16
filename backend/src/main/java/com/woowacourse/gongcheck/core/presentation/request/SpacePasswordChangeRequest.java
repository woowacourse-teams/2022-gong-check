package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SpacePasswordChangeRequest {

    @NotNull(message = "SpacePasswordChangeRequest의 password는 null일 수 없습니다.")
    private String password;

    private SpacePasswordChangeRequest() {
    }

    public SpacePasswordChangeRequest(final String password) {
        this.password = password;
    }
}
