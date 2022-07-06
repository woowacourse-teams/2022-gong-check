package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.domain.task.TaskRepository;
import com.woowacourse.gongcheck.domain.task.Tasks;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final HostRepository hostRepository;
    private final JobRepository jobRepository;
    private final TaskRepository taskRepository;
    private final RunningTaskRepository runningTaskRepository;

    public TaskService(final HostRepository hostRepository, final JobRepository jobRepository,
                       final TaskRepository taskRepository, final RunningTaskRepository runningTaskRepository) {
        this.hostRepository = hostRepository;
        this.jobRepository = jobRepository;
        this.taskRepository = taskRepository;
        this.runningTaskRepository = runningTaskRepository;
    }

    @Transactional
    public void createNewRunningTasks(final Long hostId, final Long jobId) {
        Tasks tasks = createTasksByHostIdAndJobId(hostId, jobId);
        if (existsAnyRunningTaskIn(tasks)) {
            throw new BusinessException("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.");
        }
        runningTaskRepository.saveAll(tasks.createRunningTasks());
    }

    public JobActiveResponse isJobActivated(final Long hostId, final Long jobId) {
        Tasks tasks = createTasksByHostIdAndJobId(hostId, jobId);
        return JobActiveResponse.from(existsAnyRunningTaskIn(tasks));
    }

    private Tasks createTasksByHostIdAndJobId(final Long hostId, final Long jobId) {
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
        Job job = jobRepository.findBySpaceHostAndId(host, jobId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 작업입니다."));
        return new Tasks(taskRepository.findAllBySectionJob(job));
    }

    private boolean existsAnyRunningTaskIn(final Tasks tasks) {
        return runningTaskRepository.existsByTaskIdIn(tasks.getTaskIds());
    }
}
