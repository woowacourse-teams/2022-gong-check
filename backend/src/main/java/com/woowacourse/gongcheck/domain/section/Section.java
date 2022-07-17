package com.woowacourse.gongcheck.domain.section;

import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.task.Task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "section")
@Getter
public class Section {

    private static final int NAME_MAX_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "name", length = NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "section", cascade = CascadeType.PERSIST)
    private List<Task> tasks = new ArrayList<>();

    protected Section() {
    }

    @Builder
    public Section(final Long id, final Job job, final String name, final LocalDateTime createdAt,
                   final LocalDateTime updatedAt) {
        this.id = id;
        this.job = job;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void addAllTasks(final List<Task> tasks) {
        this.tasks.addAll(tasks);
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
