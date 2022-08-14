package com.woowacourse.imagestorage.strategy;

import static com.woowacourse.imagestorage.util.ImageResizeUtil.resizedByWidth;
import static com.woowacourse.imagestorage.util.ImageTypeTransferUtil.toBufferedImage;
import static com.woowacourse.imagestorage.util.ImageTypeTransferUtil.toByteArray;

import java.awt.image.BufferedImage;

public class StaticImageResizeStrategy implements ImageResizeStrategy {

    @Override
    public byte[] resize(final byte[] originBytes, final int width, final String extension) {
        BufferedImage image = toBufferedImage(originBytes);

        return toByteArray(resizedByWidth(image, width), extension);
    }
}
