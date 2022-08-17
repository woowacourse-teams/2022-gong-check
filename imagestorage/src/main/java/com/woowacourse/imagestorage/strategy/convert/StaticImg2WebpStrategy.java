package com.woowacourse.imagestorage.strategy.convert;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import com.woowacourse.imagestorage.exception.FileConvertException;
import java.io.IOException;

public class StaticImg2WebpStrategy implements Convert2WebpStrategy {
    @Override
    public byte[] convert(final byte[] originBytes) {
        try {
            return ImmutableImage.loader()
                    .fromBytes(originBytes)
                    .bytes(WebpWriter.DEFAULT);
        } catch (IOException exception) {
            throw new FileConvertException("webp 변환 시 문제가 발생하였습니다.");
        }
    }
}
