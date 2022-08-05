package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SpaceChangeRequest {

    @NotNull(message = "이름은 null일 수 없습니다.")
    private String name;

    private String imageUrl;

    private SpaceChangeRequest() {
    }

    public SpaceChangeRequest(final String name, final String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
