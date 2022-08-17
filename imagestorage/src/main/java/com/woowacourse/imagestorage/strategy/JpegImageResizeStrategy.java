package com.woowacourse.imagestorage.strategy;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.nio.JpegWriter;
import com.woowacourse.imagestorage.exception.FileResizeException;
import java.io.IOException;

public class JpegImageResizeStrategy implements ImageResizeStrategy {

    @Override
    public byte[] resize(final byte[] originBytes, final int width, final String extension) {
        try {
            return ImmutableImage.loader()
                    .fromBytes(originBytes)
                    .resizeToWidth(width)
                    .bytes(JpegWriter.Default);
        } catch (IOException exception) {
            throw new FileResizeException("jpeg 사이즈 변환 시 문제가 발생하였습니다.");
        }
    }
}
