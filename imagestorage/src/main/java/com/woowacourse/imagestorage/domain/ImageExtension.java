package com.woowacourse.imagestorage.domain;

import com.woowacourse.imagestorage.exception.BusinessException;
import com.woowacourse.imagestorage.strategy.convert.Convert2WebpStrategy;
import com.woowacourse.imagestorage.strategy.convert.Gif2WebpStrategy;
import com.woowacourse.imagestorage.strategy.convert.StaticImg2WebpStrategy;
import com.woowacourse.imagestorage.strategy.resize.GifImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.resize.ImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.resize.JpegImageResizeStrategy;
import com.woowacourse.imagestorage.strategy.resize.PngImageResizeStrategy;
import java.util.Arrays;
import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public enum ImageExtension {

    PNG("png", MediaType.IMAGE_PNG, new PngImageResizeStrategy(), new StaticImg2WebpStrategy()),
    JPEG("jpeg", MediaType.IMAGE_JPEG, new JpegImageResizeStrategy(), new StaticImg2WebpStrategy()),
    JPG("jpg", MediaType.IMAGE_JPEG, new JpegImageResizeStrategy(), new StaticImg2WebpStrategy()),
    SVG("svg", new MediaType("image", "svg+xml"), new JpegImageResizeStrategy(), new StaticImg2WebpStrategy()),
    GIF("gif", MediaType.IMAGE_GIF, new GifImageResizeStrategy(), new Gif2WebpStrategy()),
    ;

    private final String extension;
    private final MediaType contentType;
    private final ImageResizeStrategy imageResizeStrategy;
    private final Convert2WebpStrategy convert2WebpStrategy;

    ImageExtension(final String extension, final MediaType contentType, final ImageResizeStrategy imageResizeStrategy,
                   final Convert2WebpStrategy convert2WebpStrategy) {
        this.extension = extension;
        this.contentType = contentType;
        this.imageResizeStrategy = imageResizeStrategy;
        this.convert2WebpStrategy = convert2WebpStrategy;
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

    public byte[] convertToWebp(final byte[] originBytes) {
        return convert2WebpStrategy.convert(originBytes);
    }

    private boolean containsType(final String format) {
        return extension.equals(format);
    }
}
