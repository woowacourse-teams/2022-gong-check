package com.woowacourse.imagestorage.strategy;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

class StaticImageResizeStrategyTest {

    private byte[] toByteArray(final BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private BufferedImage toBufferedImage(final byte[] bytes) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(inputStream);
    }

    @Test
    void 이미지_크기를_변환할_수_있다() throws IOException {
        StaticImageResizeStrategy strategy = new StaticImageResizeStrategy();
        byte[] inputBytes = toByteArray(new BufferedImage(100, 20, TYPE_INT_RGB));

        BufferedImage actual = toBufferedImage(strategy.resize(inputBytes, 50, "jpeg"));

        assertAll(
                () -> assertThat(actual.getWidth()).isEqualTo(50),
                () -> assertThat(actual.getHeight()).isEqualTo(10)
        );
    }
}
