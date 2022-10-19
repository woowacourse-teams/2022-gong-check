package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.auth.presentation.AuthenticationPrincipal;
import com.woowacourse.gongcheck.auth.presentation.HostOnly;
import com.woowacourse.gongcheck.core.application.SubmissionService;
import com.woowacourse.gongcheck.core.application.response.SubmissionsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(final SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping("/spaces/{spaceId}/submissions")
    @HostOnly
    public ResponseEntity<SubmissionsResponse> showSubmissions(@AuthenticationPrincipal final Long hostId,
                                                               @PathVariable final Long spaceId,
                                                               final Pageable pageable) {
        SubmissionsResponse response = submissionService.findPage(hostId, spaceId, pageable);
        return ResponseEntity.ok(response);
    }
}
