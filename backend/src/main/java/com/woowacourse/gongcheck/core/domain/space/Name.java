package com.woowacourse.gongcheck.core.domain.space;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Name {

    private static final int NAME_MAX_LENGTH = 20;

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
            throw new IllegalArgumentException("공간의 이름은 공백일 수 없습니다.");
        }

        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("공간의 이름은 " + NAME_MAX_LENGTH + "자 이하여야합니다.");
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
