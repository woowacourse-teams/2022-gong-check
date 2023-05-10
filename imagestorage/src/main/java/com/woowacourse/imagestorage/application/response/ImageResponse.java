package com.woowacourse.imagestorage.application.response;

import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public class ImageResponse {

    private byte[] bytes;
    private MediaType contentType;

    private ImageResponse() {
    }

    public ImageResponse(final byte[] bytes, final MediaType contentType) {
        this.bytes = bytes;
        this.contentType = contentType;
    }

    public static ImageResponse of(final byte[] bytes, final MediaType contentType) {
        return new ImageResponse(bytes, contentType);
    }
}
