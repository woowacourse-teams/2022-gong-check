package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.SubmissionCreatedResponse;

public interface AlertService {

    void sendMessage(final SubmissionCreatedResponse submissionCreatedResponse);
}
