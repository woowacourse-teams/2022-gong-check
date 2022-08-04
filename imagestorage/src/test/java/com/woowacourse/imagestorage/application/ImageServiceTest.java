package com.woowacourse.imagestorage.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.imagestorage.application.response.ImageResponse;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Nested
    class storeImage_메소드는 {

        @Nested
        class 저장하고자하는_이미지_파일이_입력된_경우 {

            private MultipartFile image;

            @BeforeEach
            void setUp() {
                image = new MockMultipartFile("image",
                        "jamsil.jpg",
                        "image/jpg",
                        "123".getBytes(StandardCharsets.UTF_8));
            }

            @Test
            void 이미지를_저장하고_이미지_경로를_반환한다() {
                ImageResponse actual = imageService.storeImage(image);

                assertThat(actual.getImagePath()).isNotNull();
            }
        }
    }
}
