package com.woowacourse.imagestorage.application;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.sksamuel.scrimage.ImmutableImage;
import com.woowacourse.imagestorage.ImageTypeTransfer;
import com.woowacourse.imagestorage.application.response.ImageResponse;
import com.woowacourse.imagestorage.application.response.ImageSaveResponse;
import com.woowacourse.imagestorage.exception.FileIONotFoundException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    private byte[] toByteArray(final BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Nested
    class storeImage_메소드는 {

        @Nested
        class 저장하고자하는_이미지_파일이_입력된_경우 {

            private MultipartFile image;

            @BeforeEach
            void setUp() throws IOException {
                image = new MockMultipartFile("image",
                        "jamsil.jpg",
                        "image/jpg",
                        toByteArray(new BufferedImage(500, 500, TYPE_INT_RGB)));
            }

            @Test
            void 이미지를_저장하고_이미지_경로를_반환한다() {
                ImageSaveResponse actual = imageService.storeImage(image);

                assertThat(actual.getImagePath()).isNotNull();
            }
        }
    }

    @Nested
    class getResizeImage_메소드는 {

        @Nested
        class 존재하지않는_이미지의_경로를_입력받은_경우 {

            private static final String NOT_FOUND_IMAGE_URL = "notfound.jpeg";
            private static final int WIDTH = 500;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> imageService.resizeImage(NOT_FOUND_IMAGE_URL, WIDTH, true))
                        .isInstanceOf(FileIONotFoundException.class)
                        .hasMessage("파일 경로에 파일이 존재하지 않습니다.");
            }
        }

        @Nested
        class 경로와_변경할_길이와_webp변환확인을_입력받은_경우 {

            private static final String IMAGE_URL = "test-image.jpeg";
            private static final int WIDTH = 500;

            @Test
            void 리사이징된_webp_이미지를_반환한다() throws IOException {
                ImageResponse actual = imageService.resizeImage(IMAGE_URL, WIDTH, true);
                int actualWidth = ImmutableImage.loader()
                        .fromBytes(actual.getBytes())
                        .width;

                assertAll(
                        () -> assertThat(actualWidth).isEqualTo(WIDTH),
                        () -> assertThat(actual.getContentType()).isEqualTo(new MediaType("image", "webp"))
                );
            }
        }

        @Nested
        class 경로와_변경할_길이와_webp변환불가를_입력받은_경우 {

            private static final String IMAGE_URL = "test-image.jpeg";
            private static final int WIDTH = 500;

            @Test
            void 리사이징된_기존_타입_이미지를_반환한다() {
                ImageResponse actual = imageService.resizeImage(IMAGE_URL, WIDTH, false);
                int actualWidth = ImageTypeTransfer.toBufferedImage(actual.getBytes())
                        .getWidth();

                assertAll(
                        () -> assertThat(actualWidth).isEqualTo(WIDTH),
                        () -> assertThat(actual.getContentType()).isEqualTo(MediaType.IMAGE_JPEG)
                );
            }
        }
    }
}
