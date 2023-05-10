package com.woowacourse.imagestorage;

import com.woowacourse.imagestorage.exception.FileIOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageTypeTransfer {

    public static BufferedImage toBufferedImage(final byte[] bytes) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            return ImageIO.read(inputStream);
        } catch (IOException exception) {
            throw new FileIOException("BufferedImage 변환 시 문제가 발생했습니다.");
        }
    }
}
