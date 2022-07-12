package com.woowacourse.gongcheck.domain.host;

import com.woowacourse.gongcheck.exception.UnauthorizedException;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "host")
@Getter
public class Host {

    private static final int SPACE_PASSWORD_MAX_LENGTH = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "space_password", length = SPACE_PASSWORD_MAX_LENGTH, nullable = false)
    private String spacePassword;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Host() {
    }

    @Builder
    public Host(final Long id, final String spacePassword, final LocalDateTime createdAt,
                final LocalDateTime updatedAt) {
        this.id = id;
        this.spacePassword = spacePassword;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void checkPassword(final String password) {
        if (!isSameSpacePassword(password)) {
            throw new UnauthorizedException("공간 비밀번호와 입력하신 비밀번호가 일치하지 않습니다.");
        }
    }

    private boolean isSameSpacePassword(final String password) {
        return spacePassword.equals(password);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Host host = (Host) o;
        return id.equals(host.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
