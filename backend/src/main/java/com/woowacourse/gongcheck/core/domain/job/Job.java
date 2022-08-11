package com.woowacourse.gongcheck.core.domain.job;

import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.submission.Submission;
import com.woowacourse.gongcheck.core.domain.vo.Name;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "job")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @Embedded
    private Name name;

    @Column(name = "slack_url")
    private String slackUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Job() {
    }

    public Job(final Long id, final Space space, final Name name, final String slackUrl,
               final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.space = space;
        this.name = name;
        this.slackUrl = slackUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Submission createSubmission(final String author) {
        return Submission.builder()
                .job(this)
                .author(author)
                .build();
    }

    public void changeName(final Name name) {
        this.name = name;
    }

    public void changeSlackUrl(final String slackUrl) {
        this.slackUrl = slackUrl;
    }

    public boolean hasUrl() {
        return Objects.nonNull(slackUrl);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Job job = (Job) o;
        return id.equals(job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
