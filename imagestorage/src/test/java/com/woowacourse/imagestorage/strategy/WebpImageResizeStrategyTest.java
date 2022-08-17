package com.woowacourse.imagestorage.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.sksamuel.scrimage.ImmutableImage;
import com.woowacourse.imagestorage.ImageFormatDetector;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WebpImageResizeStrategyTest {

    private final WebpImageResizeStrategy webpImageResizeStrategy = new WebpImageResizeStrategy();

    @Nested
    class resize_메소드는 {

        @Nested
        class byte값과_변경할_width를_받는_경우 {

            private static final int RESIZE_WIDTH = 500;
            private byte[] image;

            @BeforeEach
            void setUp() throws IOException {
                File imageFile = Paths.get("src/test/resources/static/images/")
                        .resolve("test-image.jpeg")
                        .toFile();
                image = IOUtils.toByteArray(new FileInputStream(imageFile));
            }

            @Test
            void width의_비율만큼_resize한_Webp이미지데이터를_반환한다() throws IOException {
                byte[] actual = webpImageResizeStrategy.resize(image, RESIZE_WIDTH);
                int actualWidth = ImmutableImage.loader()
                        .fromBytes(actual)
                        .width;

                assertAll(
                        () -> assertThat(actualWidth).isEqualTo(RESIZE_WIDTH),
                        () -> assertThat(ImageFormatDetector.isWebp(actual)).isTrue()
                );
            }
        }
    }
}
