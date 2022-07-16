package com.woowacourse.gongcheck.presentation.request;

import lombok.Getter;

@Getter
public class SpacePasswordChangeRequest {

    private String password;

    private SpacePasswordChangeRequest() {
    }

    public SpacePasswordChangeRequest(final String password) {
        this.password = password;
    }
}
