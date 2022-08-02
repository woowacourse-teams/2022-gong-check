package com.woowacourse.gongcheck.infrastructure.hash;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AES256Test {

    private final String key = "01234567890123456789012345678901";
    private AES256 aes256 = new AES256(key);

    @Test
    void 문자열을_인코딩한다() {
        String actual = aes256.encode("1");
        assertThat(actual).isNotNull();
    }

    @Test
    void 디코딩한_문자열은_인코딩한_문자열과_같다() {
        String expected = "1";
        String encode = aes256.encode(expected);

        String actual = aes256.decode(encode);

        assertThat(actual).isEqualTo(expected);
    }
}
