package com.woowacourse.imagestorage.strategy.convert;

import java.io.IOException;

public class AnimatedWebpWriter {

    /**
     * 용어는 모두 공식 스펙에 나와있는 옵션을 네이밍으로 사용 (https://developers.google.com/speed/webp/docs/gif2webp)<br> q(0~100) : RGB
     * channel에 대한 압축 계수. 값이 작을 수록 압축 속도가 빨라지지만 파일이 더 커진다. 100이 가장 최상의 품질이다.<br> m(0~6) : 사용할 압축에 대한 정도. 값이 작을 수록 압축을 하지
     * 않는다. 값이 작을 수록 처리시간이 빠르다.<br> lossy : 손실 압축 여부<br>
     */

    public static final AnimatedWebpWriter DEFAULT = new AnimatedWebpWriter();

    private static final int MIN_Q = 0;

    private static final int MAX_Q = 100;
    private static final int MIN_M = 0;
    private static final int MAX_M = 6;

    private final Gif2WebpHandler handler = new Gif2WebpHandler();

    private final int q;
    private final int m;
    private final boolean lossless;

    public AnimatedWebpWriter(final int q, final int m, final boolean lossless) {
        this.q = q;
        this.m = m;
        this.lossless = lossless;
    }

    public AnimatedWebpWriter() {
        this(-1, -1, false);
    }

    public AnimatedWebpWriter withLossless() {
        return new AnimatedWebpWriter(q, m, true);
    }

    public AnimatedWebpWriter withQ(int q) {
        if (q < MIN_Q) {
            throw new IllegalArgumentException("q must be between 0 and 100");
        }
        if (q > MAX_Q) {
            throw new IllegalArgumentException("q must be between 0 and 100");
        }
        return new AnimatedWebpWriter(q, m, lossless);
    }

    public AnimatedWebpWriter withM(int m) {
        if (m < MIN_M) {
            throw new IllegalArgumentException("m must be between 0 and 6");
        }
        if (m > MAX_M) {
            throw new IllegalArgumentException("m must be between 0 and 6");
        }
        return new AnimatedWebpWriter(q, m, lossless);
    }

    public byte[] write(final byte[] bytes) throws IOException {
        return handler.convert(bytes, q, m, lossless);
    }
}
