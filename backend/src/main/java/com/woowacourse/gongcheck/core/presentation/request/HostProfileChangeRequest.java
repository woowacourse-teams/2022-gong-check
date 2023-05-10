package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HostProfileChangeRequest {

    @NotNull(message = "nickname은 null일 수 없습니다.")
    private String nickname;

    private HostProfileChangeRequest() {
    }

    public HostProfileChangeRequest(final String nickname) {
        this.nickname = nickname;
    }
}
