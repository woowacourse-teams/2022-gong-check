package com.woowacourse.gongcheck.core.application.response;

import com.woowacourse.gongcheck.core.domain.job.Job;
import lombok.Getter;

@Getter
public class SlackUrlResponse {

    private String slackUrl;

    private SlackUrlResponse() {
    }

    public SlackUrlResponse(final String slackUrl) {
        this.slackUrl = slackUrl;
    }

    public static SlackUrlResponse from(final Job job) {
        return new SlackUrlResponse(job.getSlackUrl());
    }
}
