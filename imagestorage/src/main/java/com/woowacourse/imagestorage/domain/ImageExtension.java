package com.woowacourse.imagestorage.domain;

import com.woowacourse.imagestorage.exception.BusinessException;
import com.woowacourse.imagestorage.strategy.GifImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.ImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.JpegImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.PngImageResizeStrategy;
import java.util.Arrays;
import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public enum ImageExtension {

    PNG("png", MediaType.IMAGE_PNG, new PngImageResizeStrategy()),
    JPEG("jpeg", MediaType.IMAGE_JPEG, new JpegImageResizeStrategy()),
    JPG("jpg", MediaType.IMAGE_JPEG, new JpegImageResizeStrategy()),
    SVG("svg", new MediaType("image", "svg+xml"), new JpegImageResizeStrategy()),
    GIF("gif", MediaType.IMAGE_GIF, new GifImageResizeStrategy()),
    ;

    private final String extension;
    private final MediaType contentType;
    private final ImageResizeStrategy imageResizeStrategy;

    ImageExtension(final String extension, final MediaType contentType, final ImageResizeStrategy imageResizeStrategy) {
        this.extension = extension;
        this.contentType = contentType;
        this.imageResizeStrategy = imageResizeStrategy;
    }

    public static ImageExtension from(final String format) {
        return Arrays.stream(values())
                .filter(imageExtension -> imageExtension.containsType(format))
                .findFirst()
                .orElseThrow(() -> new BusinessException("이미지 파일 확장자만 들어올 수 있습니다."));
    }

    public byte[] resizeImage(final byte[] originBytes, final int width) {
        return imageResizeStrategy.resize(originBytes, width);
    }

    private boolean containsType(final String format) {
        return extension.equals(format);
    }
}
