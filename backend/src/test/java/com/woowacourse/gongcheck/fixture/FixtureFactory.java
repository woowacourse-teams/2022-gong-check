package com.woowacourse.gongcheck.fixture;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.task.RunningTask;
import com.woowacourse.gongcheck.domain.task.Task;
import java.time.LocalDateTime;

public class FixtureFactory {

    public static Host Host_생성(final String password) {
        return Host.builder()
                .spacePassword(password)
                .createdAt(LocalDateTime.now()).build();
    }

    public static Space Space_생성(final Host host, final String name) {
        return Space.builder()
                .host(host)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Job Job_생성(final Space space, final String name) {
        return Job.builder()
                .space(space)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Section Section_생성(final Job job, final String name) {
        return Section.builder()
                .job(job)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Task Task_생성(final Section section, final String name) {
        return Task.builder()
                .section(section)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static RunningTask RunningTask_생성(final Task task) {
        return RunningTask.builder()
                .taskId(task.getId())
                .isChecked(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
