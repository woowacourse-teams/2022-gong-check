package com.woowacourse.imagestorage.strategy.resize;

public interface ImageResizeStrategy {

    byte[] resize(byte[] originBytes, int width);
}
