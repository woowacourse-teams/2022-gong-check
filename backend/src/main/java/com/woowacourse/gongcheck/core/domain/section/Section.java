package com.woowacourse.gongcheck.core.domain.section;

import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.vo.Name;
import com.woowacourse.gongcheck.exception.BusinessException;
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
@Table(name = "section")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
public class Section {

    private static final int DESCRIPTION_MAX_LENTH = 32;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Embedded
    private Name name;

    @Column(name = "description", length = DESCRIPTION_MAX_LENTH)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Section() {
    }

    public Section(final Long id, final Job job, final Name name, final String description, final String imageUrl,
                   final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        checkDescriptionLength(description);
        this.id = id;
        this.job = job;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void checkDescriptionLength(final String description) {
        if (Objects.isNull(description)) {
            return;
        }

        if (description.length() > DESCRIPTION_MAX_LENTH) {
            throw new BusinessException("section의 설명은 " + DESCRIPTION_MAX_LENTH + "자 이하여야합니다.");
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
        Section section = (Section) o;
        return id.equals(section.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
