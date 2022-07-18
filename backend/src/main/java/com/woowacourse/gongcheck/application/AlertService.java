package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.SubmissionResponse;

public interface AlertService {

    void sendMessage(final SubmissionResponse submissionResponse);
}
