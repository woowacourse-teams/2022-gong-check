package com.woowacourse.gongcheck.application.response;

import com.woowacourse.gongcheck.domain.job.Job;
import lombok.Getter;

@Getter
public class SubmissionResponse {

    private String slackUrl;
    private String author;
    private String spaceName;
    private String jobName;

    private SubmissionResponse() {
    }

    private SubmissionResponse(final String slackUrl, final String author, final String spaceName,
                               final String jobName) {
        this.slackUrl = slackUrl;
        this.author = author;
        this.spaceName = spaceName;
        this.jobName = jobName;
    }

    public static SubmissionResponse of(final String author, final Job job) {
        return new SubmissionResponse(job.getSlackUrl(), author, job.getSpace().getName(), job.getName());
    }
}
