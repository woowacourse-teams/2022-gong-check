package com.woowacourse.imagestorage.application.response;

import lombok.Getter;

@Getter
public class ImageSaveResponse {

    private String imagePath;

    private ImageSaveResponse() {
    }

    public ImageSaveResponse(final String imagePath) {
        this.imagePath = imagePath;
    }
}
