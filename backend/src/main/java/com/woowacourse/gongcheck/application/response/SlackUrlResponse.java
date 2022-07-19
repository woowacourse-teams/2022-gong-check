package com.woowacourse.gongcheck.application.response;

import com.woowacourse.gongcheck.domain.job.Job;
import lombok.Getter;

@Getter
public class SlackUrlResponse {

    private String slackUrl;

    private SlackUrlResponse() {
    }

    private SlackUrlResponse(final String slackUrl) {
        this.slackUrl = slackUrl;
    }

    public static SlackUrlResponse from(final Job job) {
        return new SlackUrlResponse(job.getSlackUrl());
    }
}
