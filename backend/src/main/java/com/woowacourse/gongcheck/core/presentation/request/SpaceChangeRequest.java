package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SpaceChangeRequest {

    @NotNull
    private String name;

    private String imageUrl;

    private SpaceChangeRequest() {
    }

    public SpaceChangeRequest(final String name, final String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
