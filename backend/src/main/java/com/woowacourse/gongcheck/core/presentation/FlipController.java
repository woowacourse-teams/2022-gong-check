package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.core.application.TaskService;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.presentation.request.AllCheckRequest;
import com.woowacourse.gongcheck.core.presentation.request.FlipRequest;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class FlipController {

    private final TaskService taskService;
    private final SimpMessagingTemplate template;

    public FlipController(TaskService taskService, SimpMessagingTemplate template) {
        this.taskService = taskService;
        this.template = template;
    }

    @MessageMapping("/jobs/{jobId}/tasks/flip")
    public void flipRunningTask(@DestinationVariable final Long jobId, final FlipRequest request) {
        taskService.flipRunningTask(request.getTaskId());
        template.convertAndSend("/topic/jobs/" + jobId, taskService.showRunningTasks(jobId));
    }

    @MessageMapping("/jobs/{jobId}/sections/checkAll")
    public void checkAll(@DestinationVariable final Long jobId, final AllCheckRequest request) {
        taskService.checkRunningTasksInSection(request.getSectionId());
        template.convertAndSend("/topic/jobs/" + jobId, taskService.showRunningTasks(jobId));
    }

    @SubscribeMapping("/topic/jobs/{jobId}")
    public RunningTasksResponse showRunningTasks(@DestinationVariable final Long jobId) {
        return taskService.showRunningTasks(jobId);
    }
}
