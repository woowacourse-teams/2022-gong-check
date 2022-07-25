package com.woowacourse.gongcheck.core.domain.host;

import com.woowacourse.gongcheck.exception.BusinessException;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class SpacePassword {

    private static final Pattern SPACE_PASSWORD_PATTERN = Pattern.compile("^\\d{4}$");
    private static final int SPACE_PASSWORD_MAX_LENGTH = 4;

    @Column(name = "space_password", length = SPACE_PASSWORD_MAX_LENGTH, nullable = false)
    private String value;

    protected SpacePassword() {
    }

    public SpacePassword(final String value) {
        if (!SPACE_PASSWORD_PATTERN.matcher(value).find()) {
            throw new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다.");
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpacePassword password = (SpacePassword) o;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
