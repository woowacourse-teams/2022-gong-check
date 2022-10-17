package com.woowacourse.gongcheck.core.domain.host;

import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Nickname {

    @Column(name = "nickname", nullable = false)
    private String value;

    protected Nickname() {
    }

    public Nickname(final String value) {
        if (value.isBlank()) {
            throw new BusinessException("nickname은 공백일 수 없습니다.", ErrorCode.H006);
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
