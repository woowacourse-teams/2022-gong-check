package com.woowacourse.imagestorage.strategy;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.nio.AnimatedGifReader;
import com.sksamuel.scrimage.nio.GifSequenceWriter;
import com.sksamuel.scrimage.nio.ImageSource;
import com.woowacourse.imagestorage.exception.FileResizeException;
import java.io.IOException;

public class GifImageResizeStrategy implements ImageResizeStrategy {

    @Override
    public byte[] resize(final byte[] originBytes, final int width, final String extension) {
        try {
            ImmutableImage[] immutableImages = AnimatedGifReader.read(ImageSource.of(originBytes))
                    .getFrames()
                    .stream()
                    .map(immutableImage -> immutableImage.scaleToWidth(width))
                    .toArray(ImmutableImage[]::new);
            return new GifSequenceWriter().bytes(immutableImages);
        } catch (IOException exception) {
            throw new FileResizeException("gif 사이즈 변환 시 문제가 발생하였습니다.");
        }
    }
}
