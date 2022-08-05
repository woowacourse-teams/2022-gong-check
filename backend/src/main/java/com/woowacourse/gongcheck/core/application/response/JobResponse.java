package com.woowacourse.gongcheck.core.application.response;

import com.woowacourse.gongcheck.core.domain.job.Job;
import lombok.Getter;

@Getter
public class JobResponse {

    private Long id;
    private String name;

    private JobResponse() {
    }

    private JobResponse(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static JobResponse from(final Job job) {
        return new JobResponse(job.getId(), job.getName().getValue());
    }
}
