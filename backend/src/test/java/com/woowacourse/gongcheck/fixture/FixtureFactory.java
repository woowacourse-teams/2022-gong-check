package com.woowacourse.gongcheck.fixture;

import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.space.Space;
import java.time.LocalDateTime;

public class FixtureFactory {

    public static Member Member_생성(final String password) {
        return Member.builder()
                .spacePassword(password)
                .createdAt(LocalDateTime.now()).build();
    }

    public static Space Space_생성(final Member host, final String name) {
        return Space.builder()
                .member(host)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Job Job_생성(final Space space, final String name) {
        return Job.builder()
                .space(space)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
