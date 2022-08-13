package com.woowacourse.imagestorage.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.imagestorage.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ImageExtensionTest {

    @Test
    void 이미지확장자가_아닌_확장자가_입력된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> ImageExtension.from("txt"))
                .isInstanceOf(BusinessException.class)
                .hasMessage("이미지 파일 확장자만 들어올 수 있습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"png,PNG", "gif,GIF", "jpeg,JPG"})
    void 이미지확장자가_입력된_경우_ImageFormat_이_반환된다(final String input, final ImageExtension expected) {
        ImageExtension actual = ImageExtension.from(input);
        assertThat(actual).isEqualTo(expected);
    }
}
