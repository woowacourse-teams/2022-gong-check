package com.woowacourse.imagestorage.domain;

import com.woowacourse.imagestorage.exception.BusinessException;
import com.woowacourse.imagestorage.strategy.GifImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.ImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.StaticImageResizeStrategy;
import java.util.Arrays;
import java.util.List;

public enum ImageExtension {

    PNG(List.of("png"), new StaticImageResizeStrategy()),
    JPG(List.of("jpeg", "jpg"), new StaticImageResizeStrategy()),
    SVG(List.of("svg"), new StaticImageResizeStrategy()),
    GIF(List.of("gif"), new GifImageResizeStrategy()),
    ;

    private final List<String> extensions;
    private final ImageResizeStrategy imageResizeStrategy;

    ImageExtension(final List<String> extensions, final ImageResizeStrategy imageResizeStrategy) {
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
        return extensions.contains(format);
    }
}
