package com.woowacourse.gongcheck.core.domain.job;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JobTest {

    @Test
    void SlackUrl을_수정한다() {
        Job job = Job.builder().slackUrl("https://slackurl.com").build();

        job.changeSlackUrl("https://newslackurl.com");

        assertThat(job.getSlackUrl()).isEqualTo("https://newslackurl.com");
    }

    @Nested
    class url이_존재하는지_확인한다 {

        @Test
        void exist() {
            Job job = Job.builder()
                    .slackUrl("https://slackurl.com")
                    .build();

            assertThat(job.hasUrl()).isTrue();
        }

        @Test
        void nonExist() {
            Job job = Job.builder()
                    .build();

            assertThat(job.hasUrl()).isFalse();
        }
    }
}
