package com.woowacourse.gongcheck.infrastructure.hash;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AES256Test {

    private AES256 aes256 = new AES256();

    @BeforeEach
    void setUp() {
        aes256 = new AES256();
    }

    @Test
    void 최소_24_바이트_이상의_임의의_문자열로_변환한다() {
        String actual = aes256.encode("1");
        assertThat(actual.getBytes().length).isGreaterThanOrEqualTo(24);
    }

    @Test
    void 인코딩_전의_문자열로_디코딩한다() {
        String expected = "1";
        String actual = aes256.decode(aes256.encode(expected));
        assertThat(actual).isEqualTo(expected);
    }
}
