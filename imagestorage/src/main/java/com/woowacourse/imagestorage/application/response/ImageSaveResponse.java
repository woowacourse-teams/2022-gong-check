package com.woowacourse.imagestorage.application.response;

public class ImageSaveResponse {

    private String imagePath;

    private ImageSaveResponse() {
    }

    public ImageSaveResponse(final String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
