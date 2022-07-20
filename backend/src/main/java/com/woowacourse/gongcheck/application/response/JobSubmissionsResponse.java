package com.woowacourse.gongcheck.application.response;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.domain.submission.Submission;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class JobSubmissionsResponse {

    private List<JobSubmissionResponse> submissions;
    private boolean hasNext;

    private JobSubmissionsResponse() {
    }

    public JobSubmissionsResponse(final List<JobSubmissionResponse> submissions, final boolean hasNext) {
        this.submissions = submissions;
        this.hasNext = hasNext;
    }

    public static JobSubmissionsResponse from(final Slice<Submission> submissions) {
        return new JobSubmissionsResponse(
                submissions.getContent()
                        .stream()
                        .map(JobSubmissionResponse::from)
                        .collect(toList()),
                submissions.hasNext()
        );
    }

    public static JobSubmissionsResponse of(final List<Submission> submissions, final boolean hasNext) {
        return new JobSubmissionsResponse(
                submissions.stream()
                        .map(JobSubmissionResponse::from)
                        .collect(toList()),
                hasNext
        );
    }
}
