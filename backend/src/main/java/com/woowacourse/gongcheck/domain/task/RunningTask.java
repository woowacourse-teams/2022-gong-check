package com.woowacourse.gongcheck.domain.task;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "running_task")
@Getter
public class RunningTask {

    @Id
    @JoinColumn(name = "task_id")
    private Long taskId;

    @MapsId("taskId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "is_checked", nullable = false)
    @ColumnDefault("false")
    private boolean isChecked;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected RunningTask() {
    }

    @Builder
    public RunningTask(final Long taskId, final Task task, final boolean isChecked, final LocalDateTime createdAt) {
        this.taskId = taskId;
        this.task = task;
        this.isChecked = isChecked;
        this.createdAt = createdAt;
    }

    public void flipCheckedStatus() {
        isChecked = !isChecked;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RunningTask that = (RunningTask) o;
        return taskId.equals(that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}
