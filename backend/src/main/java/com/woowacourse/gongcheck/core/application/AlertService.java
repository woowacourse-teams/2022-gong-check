package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.core.application.response.SubmissionCreatedResponse;

public interface AlertService {

    void sendMessage(final SubmissionCreatedResponse submissionCreatedResponse);
}
