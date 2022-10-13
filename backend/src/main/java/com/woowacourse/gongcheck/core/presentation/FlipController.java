package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.core.application.TaskService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    public void flipRunningTask(@DestinationVariable final Long jobId, final Long taskId) {
        taskService.flip(taskId);
        template.convertAndSend("/topic/" + jobId, taskService.showRunningTasks(jobId));
    }
}
