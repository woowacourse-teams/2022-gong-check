package com.woowacourse.gongcheck.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SlackUrlChangeRequest {

    @NotNull
    private String slackUrl;

    private SlackUrlChangeRequest() {
    }

    public SlackUrlChangeRequest(final String slackUrl) {
        this.slackUrl = slackUrl;
    }
}
