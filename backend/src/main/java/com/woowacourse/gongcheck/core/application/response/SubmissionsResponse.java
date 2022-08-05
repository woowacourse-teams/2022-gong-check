package com.woowacourse.gongcheck.core.application.response;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.core.domain.submission.Submission;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class SubmissionsResponse {

    private List<SubmissionResponse> submissions;
    private boolean hasNext;

    private SubmissionsResponse() {
    }

    public SubmissionsResponse(final List<SubmissionResponse> submissions, final boolean hasNext) {
        this.submissions = submissions;
        this.hasNext = hasNext;
    }

    public static SubmissionsResponse from(final Slice<Submission> submissions) {
        return new SubmissionsResponse(
                submissions.getContent()
                        .stream()
                        .map(SubmissionResponse::from)
                        .collect(toList()),
                submissions.hasNext()
        );
    }

    public static SubmissionsResponse of(final List<Submission> submissions, final boolean hasNext) {
        return new SubmissionsResponse(
                submissions.stream()
                        .map(SubmissionResponse::from)
                        .collect(toList()),
                hasNext
        );
    }
}
