package com.woowacourse.gongcheck.domain.job;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Member_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.member.MemberRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
class JobRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Test
    void 멤버와_공간으로_조회한다() {
        Member host = memberRepository.save(Member_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job1 = Job_생성(space, "오픈");
        Job job2 = Job_생성(space, "청소");
        Job job3 = Job_생성(space, "마감");
        jobRepository.saveAll(List.of(job1, job2, job3));

        Slice<Job> result = jobRepository.findBySpaceMemberAndSpace(host, space, PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getSize()).isEqualTo(2),
                () -> assertThat(result.hasNext()).isTrue()
        );
    }
}
