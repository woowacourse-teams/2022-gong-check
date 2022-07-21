package com.woowacourse.gongcheck.domain.job;

import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.submission.Submission;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "job")
@Builder
@Getter
public class Job {

    private static final int NAME_MAX_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @Column(name = "name", length = NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "slack_url")
    private String slackUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Job() {
    }

    public Job(final Long id, final Space space, final String name, final String slackUrl,
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
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void changeSlackUrl(final String slackUrl) {
        this.slackUrl = slackUrl;
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
