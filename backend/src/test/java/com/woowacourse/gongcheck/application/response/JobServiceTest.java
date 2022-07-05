package com.woowacourse.gongcheck.application.response;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Member_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.woowacourse.gongcheck.application.SpaceService;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.member.MemberRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JobServiceTest {

    @Autowired
    private JobService jobService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Test
    void 작업_목록을_조회한다() {
        Member host = memberRepository.save(Member_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));
        Job job1 = Job_생성(space, "오픈");
        Job job2 = Job_생성(space, "청소");
        Job job3 = Job_생성(space, "마감");
        jobRepository.saveAll(List.of(job1, job2, job3));

        JobsResponse result = jobService.findPage(host.getId(), space.getId(), PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getJobs()).hasSize(2),
                () -> assertThat(result.isHasNext()).isTrue()
        );
    }

    @Test
    void 존재하지_않는_호스트로_작업_목록을_조회할_경우_예외를_던진다() {
        assertThatThrownBy(() -> jobService.findPage(0L, 1L, PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 존재하지_않는_공간의_작업_목록을_조회할_경우_예외를_던진다() {
        Member host = memberRepository.save(Member_생성("1234"));

        assertThatThrownBy(() -> jobService.findPage(host.getId(), 0L, PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }

    @Test
    void 다른_호스트의_공간의_작업_목록을_조회할_경우_예외를_던진다() {
        Member host1 = memberRepository.save(Member_생성("1234"));
        Member host2 = memberRepository.save(Member_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));

        assertThatThrownBy(() -> jobService.findPage(host1.getId(), space.getId(), PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }
}
