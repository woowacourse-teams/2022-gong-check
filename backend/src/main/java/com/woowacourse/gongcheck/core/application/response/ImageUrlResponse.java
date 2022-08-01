package com.woowacourse.gongcheck.core.application.response;

import lombok.Getter;

@Getter
public class ImageUrlResponse {

    private String imageUrl;

    private ImageUrlResponse() {
    }

    private ImageUrlResponse(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ImageUrlResponse from(final String imageUrl) {
        return new ImageUrlResponse(imageUrl);
    }
}
