package com.woowacourse.imagestorage.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.imagestorage.exception.BusinessException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class ImageFileTest {
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
        class 이미지_확장자가_아닌_파일이_입력된_경우 {

            private MultipartFile textFile;

            @BeforeEach
            void setUp() {
                textFile = new MockMultipartFile("image",
                        "jamsil.text",
                        "image/jpg",
                        "123".getBytes(StandardCharsets.UTF_8));
            }

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> ImageFile.from(textFile))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("이미지 파일 확장자만 들어올 수 있습니다.");
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
}