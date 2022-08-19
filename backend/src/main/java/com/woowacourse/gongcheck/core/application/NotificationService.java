package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.core.application.response.SubmissionCreatedResponse;

public interface NotificationService {

    void sendMessage(final SubmissionCreatedResponse submissionCreatedResponse);
}
