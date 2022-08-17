package com.woowacourse.imagestorage.strategy.resize;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.nio.PngWriter;
import com.woowacourse.imagestorage.exception.FileResizeException;
import java.io.IOException;

public class PngImageResizeStrategy implements ImageResizeStrategy {

    @Override
    public byte[] resize(final byte[] originBytes, final int width) {
        try {
            return ImmutableImage.loader()
                    .fromBytes(originBytes)
                    .scaleToWidth(width)
                    .bytes(PngWriter.NoCompression);
        } catch (IOException exception) {
            throw new FileResizeException("png 사이즈 변환 시 문제가 발생하였습니다.");
        }
    }
}
