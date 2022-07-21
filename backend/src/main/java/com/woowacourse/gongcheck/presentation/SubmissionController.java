package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.AlertService;
import com.woowacourse.gongcheck.application.SubmissionService;
import com.woowacourse.gongcheck.application.response.JobSubmissionsResponse;
import com.woowacourse.gongcheck.application.response.SubmissionCreatedResponse;
import com.woowacourse.gongcheck.presentation.aop.HostOnly;
import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import javax.validation.Valid;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final AlertService alertService;

    public SubmissionController(final SubmissionService submissionService, final AlertService alertService) {
        this.submissionService = submissionService;
        this.alertService = alertService;
    }

    @PostMapping("/jobs/{jobId}/complete")
    public ResponseEntity<Void> submitJobCompletion(@AuthenticationPrincipal final Long hostId,
                                                    @PathVariable final Long jobId,
                                                    @Valid @RequestBody final SubmissionRequest request) {
        SubmissionCreatedResponse submissionCreatedResponse = submissionService.submitJobCompletion(hostId, jobId, request);
        try {
            alertService.sendMessage(submissionCreatedResponse);
        } catch (TaskRejectedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/spaces/{spaceId}/submissions")
    @HostOnly
    public ResponseEntity<JobSubmissionsResponse> showSubmissions(@AuthenticationPrincipal final Long hostId,
                                                                  @PathVariable final Long spaceId,
                                                                  final Pageable pageable) {
        JobSubmissionsResponse response = submissionService.findPage(hostId, spaceId, pageable);
        return ResponseEntity.ok(response);
    }
}
