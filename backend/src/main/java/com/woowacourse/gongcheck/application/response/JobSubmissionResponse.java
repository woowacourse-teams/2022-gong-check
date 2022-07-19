package com.woowacourse.gongcheck.application.response;

import com.woowacourse.gongcheck.domain.submission.Submission;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class JobSubmissionResponse {

    private Long submissionId;
    private Long jobId;
    private String jobName;
    private String author;
    private LocalDateTime createdAt;

    private JobSubmissionResponse() {
    }

    private JobSubmissionResponse(final Submission submission) {
        this.submissionId = submission.getId();
        this.jobId = submission.getJob().getId();
        this.jobName = submission.getJob().getName();
        this.author = submission.getAuthor();
        this.createdAt = submission.getCreatedAt();

    }

    public static JobSubmissionResponse from(final Submission submission) {
        return new JobSubmissionResponse(submission);
    }
}
