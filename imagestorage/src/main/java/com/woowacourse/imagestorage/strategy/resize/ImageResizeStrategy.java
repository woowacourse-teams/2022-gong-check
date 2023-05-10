package com.woowacourse.imagestorage.strategy.resize;

import com.woowacourse.imagestorage.domain.ChangeWidth;

public interface ImageResizeStrategy {

    byte[] resize(byte[] originBytes, ChangeWidth width);
}
