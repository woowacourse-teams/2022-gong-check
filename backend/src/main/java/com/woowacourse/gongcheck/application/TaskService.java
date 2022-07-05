package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.member.MemberRepository;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final MemberRepository memberRepository;
    private final JobRepository jobRepository;
    private final RunningTaskRepository runningTaskRepository;

    public TaskService(final MemberRepository memberRepository, final JobRepository jobRepository,
                       final RunningTaskRepository runningTaskRepository) {
        this.memberRepository = memberRepository;
        this.jobRepository = jobRepository;
        this.runningTaskRepository = runningTaskRepository;
    }

    @Transactional
    public void createNewRunningTask(final Long hostId, final Long jobId) {
        Member host = memberRepository.findById(hostId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
        Job job = jobRepository.findBySpaceMemberAndId(host, jobId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 작업입니다."));
    }
}
