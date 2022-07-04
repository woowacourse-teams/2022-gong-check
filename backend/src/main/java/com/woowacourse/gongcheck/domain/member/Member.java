package com.woowacourse.gongcheck.domain.member;

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
@Table(name = "member")
@Getter
public class Member {

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

    protected Member() {
    }

    @Builder
    public Member(Long id, String spacePassword, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.spacePassword = spacePassword;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void checkPassword(String password) {
        if (!isSameSpacePassword(password)) {
            throw new UnauthorizedException("공간 비밀번호와 입력하신 비밀번호가 일치하지 않습니다.");
        }
    }

    private boolean isSameSpacePassword(String password) {
        return this.spacePassword.equals(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
