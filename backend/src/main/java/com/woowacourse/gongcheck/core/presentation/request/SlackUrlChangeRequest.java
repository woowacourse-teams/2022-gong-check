package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SlackUrlChangeRequest {

    @NotNull(message = "SlackUrlChangeRequest의 SlackURL은 null일 수 없습니다.")
    private String slackUrl;

    private SlackUrlChangeRequest() {
    }

    public SlackUrlChangeRequest(final String slackUrl) {
        this.slackUrl = slackUrl;
    }
}
