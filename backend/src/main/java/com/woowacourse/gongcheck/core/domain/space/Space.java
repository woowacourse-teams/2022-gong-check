package com.woowacourse.gongcheck.core.domain.space;

import com.woowacourse.gongcheck.core.domain.host.Host;
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
@Table(name = "space")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
public class Space {

    private static final int NAME_MAX_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private Host host;

    @Embedded
    private Name name;

    @Column(name = "img_url")
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Space() {
    }

    public Space(final Long id, final Host host, final Name name, final String imageUrl, final LocalDateTime createdAt,
                 final LocalDateTime updatedAt) {
        this.id = id;
        this.host = host;
        this.name = name;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void changeName(final Name name) {
        this.name = name;
    }

    public void changeImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Space space = (Space) o;
        return id.equals(space.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
