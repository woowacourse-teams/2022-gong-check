package com.woowacourse.imagestorage.domain;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.imagestorage.exception.BusinessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class ImageFileTest {

    private byte[] toByteArray(final BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Nested
    class from_메소드는 {

        @Nested
        class null인_multipartFile이_입력된_경우 {

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> ImageFile.from(null))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("이미지 파일은 null이 들어올 수 없습니다.");
            }
        }

        @Nested
        class 파일이_비어있는_multipartFile이_입력된_경우 {

            private MultipartFile emptyFile;

            @BeforeEach
            void setUp() {
                emptyFile = new MockMultipartFile("image",
                        "jamsil.jpg",
                        "image/jpg",
                        new byte[]{});
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> ImageFile.from(emptyFile))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("이미지 파일은 빈값이 들어올 수 없습니다.");
            }
        }

        @Nested
        class 파일이름이_비어있는_multipartFile이_입력된_경우 {

            private MultipartFile nullNameFile;

            @BeforeEach
            void setUp() {
                nullNameFile = new MockMultipartFile("image",
                        null,
                        "image/jpg",
                        "123".getBytes(StandardCharsets.UTF_8));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> ImageFile.from(nullNameFile))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("이미지 파일 이름은 빈값이 들어올 수 없습니다.");
            }
        }

        @Nested
        class 정상적인_multipartFile이_입력된_경우 {

            private MultipartFile multipartFile;

            @BeforeEach
            void setUp() {
                multipartFile = new MockMultipartFile("image",
                        "jamsil.jpg",
                        "image/jpg",
                        "123".getBytes(StandardCharsets.UTF_8));
            }

            @Test
            void imageFile이_반환된다() {
                assertDoesNotThrow(() -> ImageFile.from(multipartFile));
            }
        }
    }

    @Nested
    class resizeImage_메소드는 {

        @Nested
        class 변환할_가로크기가_입력되는_경우 {

            private static final int CHANGE_WIDTH = 100;
            private ImageFile imageFile;

            @BeforeEach
            void setUp() throws IOException {
                imageFile = ImageFile.from(new MockMultipartFile("image",
                        "jamsil.jpg",
                        "image/jpg",
                        toByteArray(new BufferedImage(500, 500, TYPE_INT_RGB))));
            }

            @Test
            void 변환된_imageFile이_반환된다() {
                ImageFile actual = this.imageFile.resizeImage(CHANGE_WIDTH);

                assertThat(actual.length()).isLessThan(imageFile.length());
            }
        }
    }
}
