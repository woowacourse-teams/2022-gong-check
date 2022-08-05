package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SpaceCreateRequest {

    @NotNull(message = "이름은 null일 수 없습니다.")
    private String name;

    private String imageUrl;

    private SpaceCreateRequest() {
    }

    public SpaceCreateRequest(final String name, final String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
