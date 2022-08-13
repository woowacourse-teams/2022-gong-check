package com.woowacourse.imagestorage.util;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

class ImageResizeUtilTest {

    @Test
    void 원하는_크기로_리사이즈할_수_있다() {
        BufferedImage inputImage = new BufferedImage(100, 20, TYPE_INT_RGB);
        BufferedImage actual = ImageResizeUtil.resizedByWidth(inputImage, 50);

        assertAll(
                () -> assertThat(actual.getWidth()).isEqualTo(50),
                () -> assertThat(actual.getHeight()).isEqualTo(10)
        );
    }
}
