package com.woowacourse.imagestorage.strategy.convert;

import com.woowacourse.imagestorage.exception.FileConvertException;
import com.woowacourse.imagestorage.strategy.convert.handler.Gif2WebpHandler;
import java.io.IOException;

public class Gif2WebpStrategy implements Convert2WebpStrategy {

    private static final Gif2WebpHandler DEFAULT = new Gif2WebpHandler();

    public byte[] convert(final byte[] originBytes) {
        try {
            return DEFAULT.convert(originBytes);
        } catch (IOException exception) {
            throw new FileConvertException("webp 변환 시 문제가 발생하였습니다.");
        }
    }
}
