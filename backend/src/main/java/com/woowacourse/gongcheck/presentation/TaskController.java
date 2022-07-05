package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.TaskService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createNewTasks(@AuthenticationPrincipal Long hostId,
                                               @PathVariable Long jobId) {
        taskService.createNewRunningTask(hostId, jobId);
        return ResponseEntity.created(URI.create("/api/jobs/" + jobId + "/tasks")).build();
    }
}
