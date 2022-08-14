package com.woowacourse.imagestorage.strategy;

import static com.woowacourse.imagestorage.util.ImageResizeUtil.resizedByWidth;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class GifImageResizeStrategy implements ImageResizeStrategy {

    @Override
    public byte[] resize(final byte[] originBytes, final int width, final String extension) {
        GifDecoder gifDecoder = createGifDecoder(originBytes);

        return resizeGif(gifDecoder, width).toByteArray();
    }

    private GifDecoder createGifDecoder(final byte[] originBytes) {
        InputStream inputStream = new ByteArrayInputStream(originBytes);
        GifDecoder gifDecoder = new GifDecoder();
        gifDecoder.read(inputStream);
        return gifDecoder;
    }

    private ByteArrayOutputStream resizeGif(final GifDecoder gifDecoder, final int width) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(outputStream);
        for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
            encoder.addFrame(resizedByWidth(gifDecoder.getFrame(i), width));
        }
        encoder.finish();
        return outputStream;
    }
}
