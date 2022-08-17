package com.woowacourse.imagestorage.strategy;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import com.woowacourse.imagestorage.exception.FileResizeException;
import java.io.IOException;

public class WebpImageResizeStrategy implements ImageResizeStrategy {

    @Override
    public byte[] resize(final byte[] originBytes, final int width, final String extension) {
        try {
            return ImmutableImage.loader()
                    .fromBytes(originBytes)
                    .resizeToWidth(width)
                    .bytes(WebpWriter.MAX_LOSSLESS_COMPRESSION);
        } catch (IOException exception) {
            throw new FileResizeException("webp 사이즈 변환 시 문제가 발생하였습니다.");
        }
    }
}
