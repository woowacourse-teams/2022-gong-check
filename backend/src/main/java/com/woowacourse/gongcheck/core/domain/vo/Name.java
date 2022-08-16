package com.woowacourse.gongcheck.core.domain.vo;

import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Name {

    private static final int NAME_MAX_LENGTH = 10;

    @Column(name = "name", length = NAME_MAX_LENGTH, nullable = false)
    private String value;

    protected Name() {
    }

    public Name(final String value) {
        checkNameLength(value);
        this.value = value;
    }

    private void checkNameLength(final String name) {
        if (name.isBlank()) {
            throw new BusinessException("이름은 공백일 수 없습니다.", ErrorCode.N001);
        }

        if (name.length() > NAME_MAX_LENGTH) {
            String message = String.format("이름은 " + NAME_MAX_LENGTH + "자 이하여야합니다. name = %s", name);
            throw new BusinessException(message, ErrorCode.N002);
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
        final Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
