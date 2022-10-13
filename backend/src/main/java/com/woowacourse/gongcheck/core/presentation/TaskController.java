package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.auth.presentation.AuthenticationPrincipal;
import com.woowacourse.gongcheck.auth.presentation.HostOnly;
import com.woowacourse.gongcheck.core.application.TaskService;
import com.woowacourse.gongcheck.core.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/jobs/{jobId}/runningTasks/new")
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

    @GetMapping(value = "/jobs/{jobId}/runningTasks/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connectRunningTasks(@AuthenticationPrincipal final Long hostId,
                                                          @PathVariable Long jobId) {
        SseEmitter emitter = taskService.connectRunningTasks(hostId, jobId);
        return ResponseEntity.ok(emitter);
    }

    @GetMapping("/jobs/{jobId}/runningTasks")
    public ResponseEntity<RunningTasksResponse> showRunningTasks(@AuthenticationPrincipal final Long hostId,
                                                                 @PathVariable Long jobId) {
        return ResponseEntity.ok(taskService.showRunningTasks(jobId));
    }

    @PostMapping("/tasks/{taskId}/flip")
    public ResponseEntity<Void> flipRunningTask(@AuthenticationPrincipal final Long hostId,
                                                @PathVariable final Long taskId) {
        taskService.flipRunningTask(hostId, taskId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/jobs/{jobId}/tasks")
    @HostOnly
    public ResponseEntity<TasksResponse> showTasks(@AuthenticationPrincipal final Long hostId,
                                                   @PathVariable final Long jobId) {
        TasksResponse response = taskService.findTasks(hostId, jobId);
        return ResponseEntity.ok(response);
    }
}
