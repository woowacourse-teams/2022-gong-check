package com.woowacourse.gongcheck.core.application.response;

import com.woowacourse.gongcheck.core.domain.space.Space;
import lombok.Getter;

@Getter
public class SpaceResponse {

    private Long id;
    private String name;
    private String imageUrl;

    private SpaceResponse() {
    }

    private SpaceResponse(final Long id, final String name, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static SpaceResponse from(final Space space) {
        return new SpaceResponse(space.getId(), space.getName(), space.getImageUrl());
    }
}
