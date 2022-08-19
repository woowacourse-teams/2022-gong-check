package com.woowacourse.imagestorage.strategy.resize;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.nio.JpegWriter;
import com.woowacourse.imagestorage.domain.ChangeWidth;
import com.woowacourse.imagestorage.exception.FileResizeException;
import java.io.IOException;

public class JpegImageResizeStrategy implements ImageResizeStrategy {

    @Override
    public byte[] resize(final byte[] originBytes, final ChangeWidth width) {
        try {
            return ImmutableImage.loader()
                    .fromBytes(originBytes)
                    .scaleToWidth(width.getValue())
                    .bytes(JpegWriter.Default);
        } catch (IOException exception) {
            throw new FileResizeException("jpeg 사이즈 변환 시 문제가 발생하였습니다.");
        }
    }
}
