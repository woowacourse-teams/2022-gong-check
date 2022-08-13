package com.woowacourse.imagestorage.domain;

import com.woowacourse.imagestorage.exception.BusinessException;
import com.woowacourse.imagestorage.strategy.GifImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.ImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.StaticImageResizeStrategy;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ImageExtension {

    PNG("png", new StaticImageResizeStrategy()),
    JPEG("jpeg", new StaticImageResizeStrategy()),
    JPG("jpg", new StaticImageResizeStrategy()),
    SVG("svg", new StaticImageResizeStrategy()),
    GIF("gif", new GifImageResizeStrategy()),
    ;

    private final String extensions;
    private final ImageResizeStrategy imageResizeStrategy;

    ImageExtension(final String extensions, final ImageResizeStrategy imageResizeStrategy) {
        this.extensions = extensions;
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
        return extensions.equals(format);
    }
}
