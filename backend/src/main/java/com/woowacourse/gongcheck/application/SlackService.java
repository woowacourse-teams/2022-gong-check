package com.woowacourse.gongcheck.application;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.woowacourse.gongcheck.application.response.Attachments;
import com.woowacourse.gongcheck.application.response.SubmissionResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SlackService implements AlertService {

    @Async
    @Override
    public void sendMessage(final SubmissionResponse submissionResponse) {
        try (Slack slack = Slack.getInstance()) {
            Payload payload = Payload.builder()
                    .attachments(Attachments.of(submissionResponse).getAttachments())
                    .build();
            slack.send(submissionResponse.getSlackUrl(), payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
