package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Member_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.member.MemberRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RunningTaskRepository runningTaskRepository;

    @Test
    void 존재하지_않는_호스트로_새로운_작업을_진행하려하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> taskService.createNewRunningTask(0L, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 존재하지_않는_작업의_새로운_작업을_진행하려는_경우_예외가_발생한다() {
        Member host = memberRepository.save(Member_생성("1234"));

        assertThatThrownBy(() -> taskService.createNewRunningTask(host.getId(), 0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }

    @Test
    void 다른_호스트의_작업의_새로운_작업을_진행하려는_경우_예외가_발생한다() {
        Member host1 = memberRepository.save(Member_생성("1234"));
        Member host2 = memberRepository.save(Member_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host2, "잠실"));
        Job job = jobRepository.save(Job_생성(space, "청소"));

        assertThatThrownBy(() -> taskService.createNewRunningTask(host1.getId(), job.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 작업입니다.");
    }
}
