package com.woowacourse.imagestorage.util;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

class ImageTypeTransferUtilTest {

    @Test
    void BufferedImage를_byte_array로_변환할_수_있다() {
        BufferedImage inputImage = new BufferedImage(100, 20, TYPE_INT_RGB);
        assertDoesNotThrow(() -> ImageTypeTransferUtil.toByteArray(inputImage, "jpeg"));
    }

    @Test
    void byte_array를_BufferedImage로_변환할_수_있다() {
        byte[] inputBytes = new byte[]{};
        assertDoesNotThrow(() -> ImageTypeTransferUtil.toBufferedImage(inputBytes));
    }
}
