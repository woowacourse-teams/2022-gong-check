package com.woowacourse.imagestorage.application.response;

public class ImageResponse {

    private String imagePath;

    private ImageResponse() {
    }

    public ImageResponse(final String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
