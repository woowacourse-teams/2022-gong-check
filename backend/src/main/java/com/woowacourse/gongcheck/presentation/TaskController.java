package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.TaskService;
import com.woowacourse.gongcheck.application.response.JobActiveResponse;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/jobs/{jobId}/tasks/new")
    public ResponseEntity<Void> createNewTasks(@AuthenticationPrincipal final Long hostId,
                                               @PathVariable final Long jobId) {
        taskService.createNewRunningTasks(hostId, jobId);
        return ResponseEntity.created(URI.create("/api/jobs/" + jobId + "/tasks")).build();
    }

    @GetMapping("/jobs/{jobId}/active")
    public ResponseEntity<JobActiveResponse> isJobActive(@AuthenticationPrincipal final Long hostId,
                                                         @PathVariable final Long jobId) {
        JobActiveResponse response = taskService.isJobActivated(hostId, jobId);
        return ResponseEntity.ok(response);
    }
}
