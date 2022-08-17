package com.woowacourse.imagestorage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ImageFormatDetector {

    // inspired by http://stackoverflow.com/questions/22534833/scala-detect-mimetype-of-an-arraybyte-image
    private static final byte[] GIF = new byte[]{'G', 'I', 'F', '8'};
    private static final byte[] PNG = new byte[]{(byte) 0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A};
    private static final byte[] JPEG = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
    // see webp spec https://developers.google.com/speed/webp/docs/riff_container
    private static final byte[] WEBP_START = new byte[]{'R', 'I', 'F', 'F'};
    private static final byte[] WEBP_END = new byte[]{'W', 'E', 'B', 'P'};

    public static boolean isGif(final byte[] bytes) {
        for (int i = 0; i < GIF.length; i++) {
            if (isNotSameByte(GIF[i], bytes[i])) {
                return false;
            }
        }
        return true;
    }

    @Test
    void gif파일을_판별할_수_있다() {
        boolean actual = ImageFormatDetector
                .isGif(new byte[]{'G', 'I', 'F', '8', 'T', 'E', 'S', 'T'});

        assertThat(actual).isTrue();
    }

    public static boolean isPng(final byte[] bytes) {
        for (int i = 0; i < PNG.length; i++) {
            if (isNotSameByte(PNG[i], bytes[i])) {
                return false;
            }
        }
        return true;
    }

    @Test
    void png파일을_판별할_수_있다() {
        boolean actual = ImageFormatDetector
                .isPng(new byte[]{(byte) 0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A, 'T', 'E', 'S', 'T'});

        assertThat(actual).isTrue();
    }

    public static boolean isJpeg(final byte[] bytes) {
        for (int i = 0; i < JPEG.length; i++) {
            if (isNotSameByte(JPEG[i], bytes[i])) {
                return false;
            }
        }
        return true;
    }

    @Test
    void jpeg파일을_판별할_수_있다() {
        boolean actual = ImageFormatDetector
                .isJpeg(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, 'T', 'E', 'S', 'T'});

        assertThat(actual).isTrue();
    }

    public static boolean isWebp(final byte[] bytes) {
        for (int i = 0; i < WEBP_START.length; i++) {
            if (isNotSameByte(WEBP_START[i], bytes[i])) {
                return false;
            }
        }
        for (int i = 0; i < WEBP_END.length; i++) {
            if (isNotSameByte(WEBP_END[i], bytes[i + 8])) {
                return false;
            }
        }
        return true;
    }

    @Test
    void webp파일을_판별할_수_있다() {
        boolean actual = ImageFormatDetector
                .isWebp(new byte[]{'R', 'I', 'F', 'F', 'T', 'E', 'S', 'T', 'W', 'E', 'B', 'P'});

        assertThat(actual).isTrue();
    }

    private static boolean isNotSameByte(final byte origin, final byte compare) {
        return origin != compare;
    }
}
