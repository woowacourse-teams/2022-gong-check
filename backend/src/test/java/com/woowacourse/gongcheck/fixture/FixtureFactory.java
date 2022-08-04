package com.woowacourse.gongcheck.fixture;

import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.SpacePassword;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.space.Name;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.submission.Submission;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.Task;
import java.time.LocalDateTime;

public class FixtureFactory {

    public static Host Host_생성(final String password, final Long githubId) {
        return Host.builder()
                .spacePassword(new SpacePassword(password))
                .githubId(githubId)
                .imageUrl("test.com")
                .createdAt(LocalDateTime.now()).build();
    }

    public static Space Space_생성(final Host host, final String name) {
        return Space.builder()
                .host(host)
                .name(new Name(name))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Space Space_아이디_지정_생성(final Long id, final Host host, final String name) {
        return Space.builder()
                .id(id)
                .host(host)
                .name(new Name(name))
                .imageUrl("image.url")
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

    public static Job Job_생성(final Space space, final String name, final String slackUrl) {
        return Job.builder()
                .space(space)
                .name(name)
                .slackUrl(slackUrl)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Job Job_아이디_지정_생성(final Long id, final Space space, final String name) {
        return Job.builder()
                .id(id)
                .space(space)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Section Section_생성(final Job job, final String name) {
        return Section.builder()
                .job(job)
                .name(name)
                .description("설명")
                .imageUrl("image.url")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Section Section_아이디_지정_생성(final Long id, final Job job, final String name) {
        return Section.builder()
                .id(id)
                .job(job)
                .name(name)
                .description("설명")
                .imageUrl("image.url")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Task Task_생성(final Section section, final String name) {
        return Task.builder()
                .section(section)
                .name(name)
                .description("설명")
                .imageUrl("image.url")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Task Task_아이디_지정_생성(final Long id, final Section section, final String name) {
        return Task.builder()
                .id(id)
                .section(section)
                .name(name)
                .description("설명")
                .imageUrl("image.url")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Task RunningTask로_Task_아이디_지정_생성(final Long id, final RunningTask runningTask, final Section section,
                                                   final String name) {
        return Task.builder()
                .id(id)
                .section(section)
                .runningTask(runningTask)
                .name(name)
                .description("설명")
                .imageUrl("image.url")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static RunningTask RunningTask_생성(final Long taskId, final boolean isChecked) {
        return RunningTask.builder()
                .taskId(taskId)
                .isChecked(isChecked)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Submission Submission_생성(final Job job, final String author) {
        return Submission.builder()
                .job(job)
                .author(author)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Submission Submission_아이디_지정_생성(final Long id, final Job job) {
        return Submission.builder()
                .id(id)
                .job(job)
                .author("어썸오")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
