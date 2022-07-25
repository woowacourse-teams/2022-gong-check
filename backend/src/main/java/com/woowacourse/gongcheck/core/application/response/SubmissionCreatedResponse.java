package com.woowacourse.gongcheck.core.application.response;

import com.woowacourse.gongcheck.core.domain.job.Job;
import lombok.Getter;

@Getter
public class SubmissionCreatedResponse {

    private String slackUrl;
    private String author;
    private String spaceName;
    private String jobName;

    private SubmissionCreatedResponse() {
    }

    private SubmissionCreatedResponse(final String slackUrl, final String author, final String spaceName,
                                      final String jobName) {
        this.slackUrl = slackUrl;
        this.author = author;
        this.spaceName = spaceName;
        this.jobName = jobName;
    }

    public static SubmissionCreatedResponse of(final String author, final Job job) {
        return new SubmissionCreatedResponse(job.getSlackUrl(), author, job.getSpace().getName(), job.getName());
    }
}
