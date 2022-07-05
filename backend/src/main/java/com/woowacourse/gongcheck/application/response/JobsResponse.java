package com.woowacourse.gongcheck.application.response;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.domain.job.Job;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class JobsResponse {

    private List<JobResponse> jobs;
    private boolean hasNext;

    private JobsResponse() {
    }

    private JobsResponse(final List<JobResponse> jobs, final boolean hasNext) {
        this.jobs = jobs;
        this.hasNext = hasNext;
    }

    public static JobsResponse from(final Slice<Job> jobs) {
        return new JobsResponse(
                jobs.getContent()
                        .stream()
                        .map(JobResponse::from)
                        .collect(toList()),
                jobs.hasNext()
        );
    }
}
