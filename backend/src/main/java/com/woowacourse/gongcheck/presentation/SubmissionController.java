package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.SubmissionService;
import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(final SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/jobs/{jobId}/complete")
    public ResponseEntity<Void> submitJobCompletion(@AuthenticationPrincipal final Long hostId,
                                                    @PathVariable final Long jobId,
                                                    @Valid @RequestBody final SubmissionRequest request) {
        submissionService.submitJobCompletion(hostId, jobId, request);
        return ResponseEntity.ok().build();
    }
}
