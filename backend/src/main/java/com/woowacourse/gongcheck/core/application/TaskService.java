package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.core.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.TaskRepository;
import com.woowacourse.gongcheck.core.domain.task.Tasks;
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
        Tasks tasks = findTasksByHostIdAndJobId(hostId, jobId);
        if (tasks.getTasks().isEmpty()) {
            throw new NotFoundException("작업이 존재하지 않습니다.");
        }
        if (existsAnyRunningTaskIn(tasks)) {
            throw new BusinessException("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.");
        }
        runningTaskRepository.saveAll(tasks.createRunningTasks());
    }

    public JobActiveResponse isJobActivated(final Long hostId, final Long jobId) {
        Tasks tasks = findTasksByHostIdAndJobId(hostId, jobId);
        return JobActiveResponse.from(existsAnyRunningTaskIn(tasks));
    }

    public RunningTasksResponse findRunningTasks(final Long hostId, final Long jobId) {
        Tasks tasks = findTasksByHostIdAndJobId(hostId, jobId);
        return findExistingRunningTasks(tasks);
    }

    @Transactional
    public void flipRunningTask(final Long hostId, final Long taskId) {
        Host host = hostRepository.getById(hostId);
        Task task = taskRepository.getBySectionJobSpaceHostAndId(host, taskId);
        RunningTask runningTask = runningTaskRepository.findByTaskId(task.getId())
                .orElseThrow(() -> new BusinessException("현재 진행 중인 작업이 아닙니다."));

        runningTask.flipCheckedStatus();
    }

    public TasksResponse findTasks(final Long hostId, final Long jobId) {
        Tasks tasks = findTasksByHostIdAndJobId(hostId, jobId);
        return TasksResponse.from(tasks);
    }

    private Tasks findTasksByHostIdAndJobId(final Long hostId, final Long jobId) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        return new Tasks(taskRepository.findAllBySectionJob(job));
    }

    private RunningTasksResponse findExistingRunningTasks(final Tasks tasks) {
        if (!existsAnyRunningTaskIn(tasks)) {
            throw new BusinessException("현재 진행중인 작업이 존재하지 않아 조회할 수 없습니다");
        }
        return RunningTasksResponse.from(tasks);
    }

    private boolean existsAnyRunningTaskIn(final Tasks tasks) {
        return runningTaskRepository.existsByTaskIdIn(tasks.getTaskIds());
    }
}
