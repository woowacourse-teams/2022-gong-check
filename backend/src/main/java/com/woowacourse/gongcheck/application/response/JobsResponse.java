package com.woowacourse.gongcheck.application.response;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.domain.job.Job;
import java.util.List;
import lombok.Getter;

@Getter
public class JobsResponse {

    private List<JobResponse> jobs;

    private JobsResponse() {
    }

    private JobsResponse(final List<JobResponse> jobs) {
        this.jobs = jobs;
    }

    public static JobsResponse from(final List<Job> jobs) {
        return new JobsResponse(
                jobs.stream()
                        .map(JobResponse::from)
                        .collect(toList())
        );
    }
}
