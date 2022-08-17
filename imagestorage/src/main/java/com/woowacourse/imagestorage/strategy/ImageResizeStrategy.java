package com.woowacourse.imagestorage.strategy;

public interface ImageResizeStrategy {

    byte[] resize(byte[] originBytes, int width);
}
