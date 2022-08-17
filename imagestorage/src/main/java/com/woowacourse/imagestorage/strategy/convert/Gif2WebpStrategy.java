package com.woowacourse.imagestorage.strategy.convert;

import com.woowacourse.imagestorage.convert.AnimatedWebpWriter;
import com.woowacourse.imagestorage.exception.FileConvertException;
import java.io.IOException;

public class Gif2WebpStrategy implements Convert2WebpStrategy {

    public byte[] convert(final byte[] originBytes) {
        try {
            return AnimatedWebpWriter.DEFAULT.write(originBytes);
        } catch (IOException exception) {
            throw new FileConvertException("webp 변환 시 문제가 발생하였습니다.");
        }
    }
}
