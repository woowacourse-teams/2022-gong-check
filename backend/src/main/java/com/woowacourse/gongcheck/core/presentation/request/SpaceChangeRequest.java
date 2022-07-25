package com.woowacourse.gongcheck.core.presentation.request;

import lombok.Getter;

@Getter
public class SpaceChangeRequest {

    private String name;

    private SpaceChangeRequest() {
    }

    public SpaceChangeRequest(final String name) {
        this.name = name;
    }
}
