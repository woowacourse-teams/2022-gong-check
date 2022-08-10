package com.woowacourse.imagestorage.application;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

import com.woowacourse.imagestorage.application.response.ImageResponse;
import com.woowacourse.imagestorage.util.ImageTypeUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
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

            private final MockedStatic<ImageTypeUtil> imageTypeUtil = mockStatic(ImageTypeUtil.class);
            private MultipartFile image;

            @BeforeEach
            void setUp() throws IOException {
                byte[] imageBytes = "123456".getBytes(StandardCharsets.UTF_8);
                image = new MockMultipartFile("image",
                        "jamsil.jpg",
                        "image/jpg",
                        imageBytes);

                given(ImageTypeUtil.toBufferedImage(imageBytes)).willReturn(new BufferedImage(500, 500, TYPE_INT_RGB));
                given(ImageTypeUtil.toByteArray(any(), any())).willReturn("123456".getBytes(StandardCharsets.UTF_8));
            }

            @AfterEach
            void tearDown() {
                imageTypeUtil.close();
            }

            @Test
            void 이미지를_저장하고_이미지_경로를_반환한다() throws IOException {
                ImageResponse actual = imageService.storeImage(image);

                assertThat(actual.getImagePath()).isNotNull();
            }
        }
    }
}
