package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.core.application.SubmissionService;
import com.woowacourse.gongcheck.core.presentation.request.SubmissionRequest;
import javax.validation.Valid;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SubmitController {

    private final SubmissionService submissionService;
    private final SimpMessagingTemplate template;

    public SubmitController(SubmissionService submissionService, SimpMessagingTemplate template) {
        this.submissionService = submissionService;
        this.template = template;
    }

    @MessageMapping("/jobs/{jobId}/complete")
    public Boolean submitJobCompletion(@DestinationVariable Long jobId, @Valid final SubmissionRequest request) {
        submissionService.submitJobCompletion(jobId, request);
        return true;
    }
}
