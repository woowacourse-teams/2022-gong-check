package com.woowacourse.gongcheck.core.domain.submission;

import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "submission")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
public class Submission {

    private static final int AUTHOR_MAX_LENGTH = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "author", length = AUTHOR_MAX_LENGTH, nullable = false)
    private String author;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Submission() {
    }

    public Submission(final Long id, final Job job, final String author, final LocalDateTime createdAt) {
        validateAuthorLength(author);
        this.id = id;
        this.job = job;
        this.author = author;
        this.createdAt = createdAt;
    }

    private void validateAuthorLength(final String author) {
        if (author.isBlank()) {
            String message = String.format("제출자 이름은 공백일 수 없습니다. author = %s", author);
            throw new BusinessException(message, ErrorCode.S002);
        }

        if (author.length() > AUTHOR_MAX_LENGTH) {
            String message = String.format("제출자 이름은 %d자 이하여야 합니다. author = %s", AUTHOR_MAX_LENGTH, author);
            throw new BusinessException(message, ErrorCode.S003);
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
        Submission that = (Submission) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
