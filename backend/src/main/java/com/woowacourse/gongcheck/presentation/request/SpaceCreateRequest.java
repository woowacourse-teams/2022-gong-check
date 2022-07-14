package com.woowacourse.gongcheck.presentation.request;

import lombok.Getter;

@Getter
public class SpaceCreateRequest {

    private String name;
    private String imageUrl;

    private SpaceCreateRequest() {
    }

    public SpaceCreateRequest(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
