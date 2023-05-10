package com.woowacourse.imagestorage.strategy.convert;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.imagestorage.ImageFormatDetector;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class Gif2WebpStrategyTest {

    private final Gif2WebpStrategy gif2WebpStrategy = new Gif2WebpStrategy();

    @Nested
    class convert_메소드는 {

        @Nested
        class byte값를_받는_경우 {

            private byte[] image;

            @BeforeEach
            void setUp() throws IOException {
                File imageFile = Paths.get("src/test/resources/static/images/")
                        .resolve("test-image.gif")
                        .toFile();
                image = IOUtils.toByteArray(new FileInputStream(imageFile));
            }

            @Test
            void convert한_Webp이미지데이터를_반환한다() {
                byte[] actual = gif2WebpStrategy.convert(image);

                assertThat(ImageFormatDetector.isWebp(actual)).isTrue();
            }
        }
    }
}
