package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.auth.presentation.AuthenticationPrincipal;
import com.woowacourse.gongcheck.auth.presentation.aop.HostOnly;
import com.woowacourse.gongcheck.core.application.JobService;
import com.woowacourse.gongcheck.core.application.response.JobsResponse;
import com.woowacourse.gongcheck.core.application.response.SlackUrlResponse;
import com.woowacourse.gongcheck.core.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.core.presentation.request.SlackUrlChangeRequest;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                                                 @PathVariable final Long spaceId) {
        JobsResponse response = jobService.findJobs(hostId, spaceId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/spaces/{spaceId}/jobs")
    @HostOnly
    public ResponseEntity<Void> createJob(@AuthenticationPrincipal final Long hostId,
                                          @PathVariable final Long spaceId,
                                          @Valid @RequestBody final JobCreateRequest request) {
        Long savedJobId = jobService.createJob(hostId, spaceId, request);
        return ResponseEntity.created(URI.create("/api/spaces/" + savedJobId + "/jobs")).build();
    }

    @PutMapping("/jobs/{jobId}")
    @HostOnly
    public ResponseEntity<Void> updateJob(@AuthenticationPrincipal final Long hostId,
                                          @PathVariable final Long jobId,
                                          @Valid @RequestBody final JobCreateRequest request) {
        jobService.updateJob(hostId, jobId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/jobs/{jobId}")
    @HostOnly
    public ResponseEntity<Void> removeJob(@AuthenticationPrincipal final Long hostId, @PathVariable final Long jobId) {
        jobService.removeJob(hostId, jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/jobs/{jobId}/slack")
    @HostOnly
    public ResponseEntity<SlackUrlResponse> findSlackUrl(@AuthenticationPrincipal final Long hostId,
                                                         @PathVariable final Long jobId) {
        SlackUrlResponse response = jobService.findSlackUrl(hostId, jobId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/jobs/{jobId}/slack")
    @HostOnly
    public ResponseEntity<Void> changeSlackUrl(@AuthenticationPrincipal final Long hostId,
                                               @PathVariable final Long jobId,
                                               @Valid @RequestBody final SlackUrlChangeRequest request) {
        jobService.changeSlackUrl(hostId, jobId, request);
        return ResponseEntity.noContent().build();
    }
}
