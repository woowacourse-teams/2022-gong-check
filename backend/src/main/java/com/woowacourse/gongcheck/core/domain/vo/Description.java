package com.woowacourse.gongcheck.core.domain.vo;

import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Description {

    private static final int DESCRIPTION_MAX_LENGTH = 128;

    @Column(name = "description", length = DESCRIPTION_MAX_LENGTH)
    private String value;

    protected Description() {
    }

    public Description(final String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(final String value) {
        if (value.length() > DESCRIPTION_MAX_LENGTH) {
            String message = String.format("설명은 %d자 이하여야 합니다. value = %s", DESCRIPTION_MAX_LENGTH, value);
            throw new BusinessException(message, ErrorCode.D001);
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
        Description that = (Description) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
