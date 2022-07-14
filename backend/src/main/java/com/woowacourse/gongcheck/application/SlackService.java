package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.SubmissionResponse;

public interface SlackService {

    void sendMessage(final SubmissionResponse submissionResponse);
}
