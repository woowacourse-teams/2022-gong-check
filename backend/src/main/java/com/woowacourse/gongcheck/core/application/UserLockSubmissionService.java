package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.core.domain.lock.UserLevelLock;
import com.woowacourse.gongcheck.core.presentation.request.SubmissionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserLockSubmissionService {

    private final UserLevelLock userLevelLock;
    private final SubmissionService submissionService;

    public UserLockSubmissionService(final UserLevelLock userLevelLock, final SubmissionService submissionService) {
        this.userLevelLock = userLevelLock;
        this.submissionService = submissionService;
    }

    @Transactional
    public void submitJobCompletionByLock(final Long hostId, final Long jobId, final SubmissionRequest request) {
        userLevelLock.executeWithLock(
                String.valueOf(jobId),
                30,
                () -> submissionService.submitJobCompletion(hostId, jobId, request)
        );
    }
}
