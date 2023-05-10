package com.woowacourse.imagestorage.domain;

import com.woowacourse.imagestorage.exception.BusinessException;
import java.util.Objects;
import lombok.Getter;

@Getter
public class ChangeWidth {

    private static final int MIN_WIDTH = 10;

    private final int value;

    public ChangeWidth(final int value) {
        checkAvaliableWidht(value);
        this.value = value;
    }

    private void checkAvaliableWidht(final int value) {
        if (value < MIN_WIDTH) {
            throw new BusinessException("변경할 가로 길이가 작습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChangeWidth that = (ChangeWidth) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
