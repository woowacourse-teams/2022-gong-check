package com.woowacourse.imagestorage.convert;

import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

class Gif2WebpHandlerTest {

    @Test
    void testset() {
        if (SystemUtils.IS_OS_WINDOWS) {
            System.out.println(1);
        }
        if (SystemUtils.IS_OS_MAC) {
            System.out.println(2);
        }
        if (SystemUtils.IS_OS_LINUX) {
            System.out.println(3);
        }
        if (SystemUtils.IS_OS_MAC_OSX) {
            System.out.println(4);
        }
        if (SystemUtils.IS_OS_ZOS) {
            System.out.println(5);
        }
    }
}
