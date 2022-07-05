package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.response.JobService;
import com.woowacourse.gongcheck.application.response.JobsResponse;
import com.woowacourse.gongcheck.application.response.SpacesResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JobController {

    private final JobService jobService;

    public JobController(final JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/spaces/{spaceId}/jobs")
    public ResponseEntity<JobsResponse> showJobs(@AuthenticationPrincipal final Long hostId,
                                                 @PathVariable final Long spaceId,
                                                 final Pageable pageable) {
        JobsResponse response = jobService.findPage(hostId, spaceId, pageable);
        return ResponseEntity.ok(response);
    }
}
