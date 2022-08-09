package com.woowacourse.gongcheck.core.domain.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.core.domain.vo.Name;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JobTest {

    @Test
    void SlackUrl을_수정한다() {
        Job job = Job.builder()
                .slackUrl("https://slackurl.com")
                .build();

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

    @Test
    void 이름을_수정한다() {
        Name name = new Name("수정 전 이름");
        Name changeName = new Name("수정 후 이름");
        Job job = Job.builder()
                .name(name)
                .build();

        job.changeName(changeName);

        assertThat(job.getName()).isEqualTo(changeName);
    }
}
