package com.woowacourse.gongcheck.infrastructure.notification;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.woowacourse.gongcheck.core.application.NotificationService;
import com.woowacourse.gongcheck.core.application.response.Attachments;
import com.woowacourse.gongcheck.core.application.response.SubmissionCreatedResponse;
import com.woowacourse.gongcheck.exception.InfrastructureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackService implements NotificationService {

    @Async
    @Override
    public void sendMessage(final SubmissionCreatedResponse submissionCreatedResponse) {
        try (Slack slack = Slack.getInstance()) {
            Payload payload = Payload.builder()
                    .attachments(Attachments.of(submissionCreatedResponse).getAttachments())
                    .build();
            slack.send(submissionCreatedResponse.getSlackUrl(), payload);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InfrastructureException("메시지 전송에 실패했습니다.");
        }
    }
}
