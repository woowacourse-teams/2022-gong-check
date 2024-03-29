package com.woowacourse.gongcheck.core.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongcheck.core.domain.submission.Submission;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SubmissionResponse {

    private Long submissionId;
    private Long jobId;
    private String jobName;
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    private SubmissionResponse() {
    }

    private SubmissionResponse(final Long submissionId, final Long jobId, final String jobName, final String author,
                               final LocalDateTime createdAt) {
        this.submissionId = submissionId;
        this.jobId = jobId;
        this.jobName = jobName;
        this.author = author;
        this.createdAt = createdAt;
    }

    public static SubmissionResponse from(final Submission submission) {
        return new SubmissionResponse(submission.getId(), submission.getJob().getId(),
                submission.getJob().getName().getValue(),
                submission.getAuthor(), submission.getCreatedAt());
    }
}
