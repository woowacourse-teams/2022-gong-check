package com.woowacourse.imagestorage.util;

import com.woowacourse.imagestorage.exception.FileIOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageTypeTransferUtil {

    private ImageTypeTransferUtil() {
        throw new AssertionError();
    }

    public static byte[] toByteArray(final BufferedImage image, final String format) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, format, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException exception) {
            throw new FileIOException("byte array 변환 시 문제가 발생했습니다.");
        }
    }

    public static BufferedImage toBufferedImage(final byte[] bytes) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            return ImageIO.read(inputStream);
        } catch (IOException exception) {
            throw new FileIOException("BufferedImage 변환 시 문제가 발생했습니다.");
        }
    }
}
