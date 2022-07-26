package com.woowacourse.gongcheck.core.domain.job;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JobTest {

    @Test
    void SlackUrl을_수정한다() {
        Job job = Job.builder().slackUrl("https://slackurl.com").build();

        job.changeSlackUrl("https://newslackurl.com");

        assertThat(job.getSlackUrl()).isEqualTo("https://newslackurl.com");
    }
}
