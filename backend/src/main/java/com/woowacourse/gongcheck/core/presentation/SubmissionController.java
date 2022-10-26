package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.core.application.SubmissionService;
import com.woowacourse.gongcheck.core.presentation.request.SubmissionRequest;
import javax.validation.Valid;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(final SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @MessageMapping("/jobs/{jobId}/complete")
    public Boolean submitJobCompletion(@DestinationVariable final Long jobId, @Valid final SubmissionRequest request) {
        submissionService.submitJobCompletion(jobId, request);
        return true;
    }
}
