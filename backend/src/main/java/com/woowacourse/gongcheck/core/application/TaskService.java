package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.core.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.section.SectionRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskSseEmitterContainer;
import com.woowacourse.gongcheck.core.domain.task.RunningTasks;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.TaskRepository;
import com.woowacourse.gongcheck.core.domain.task.Tasks;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import com.woowacourse.gongcheck.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final HostRepository hostRepository;
    private final JobRepository jobRepository;
    private final SectionRepository sectionRepository;
    private final TaskRepository taskRepository;
    private final RunningTaskRepository runningTaskRepository;
    private final RunningTaskSseEmitterContainer runningTaskSseEmitterContainer;

    public TaskService(final HostRepository hostRepository, final JobRepository jobRepository,
                       final SectionRepository sectionRepository, final TaskRepository taskRepository,
                       final RunningTaskRepository runningTaskRepository,
                       final RunningTaskSseEmitterContainer runningTaskSseEmitterContainer) {
        this.hostRepository = hostRepository;
        this.jobRepository = jobRepository;
        this.sectionRepository = sectionRepository;
        this.taskRepository = taskRepository;
        this.runningTaskRepository = runningTaskRepository;
        this.runningTaskSseEmitterContainer = runningTaskSseEmitterContainer;
    }

    @Transactional
    public void createNewRunningTasks(final Long hostId, final Long jobId) {
        Tasks tasks = findTasksByHostIdAndJobId(hostId, jobId);
        if (tasks.isEmpty()) {
            String message = String.format("작업이 존재하지 않습니다. hostId = %d, jobId = %d", hostId, jobId);
            throw new NotFoundException(message, ErrorCode.T002);
        }
        if (existsAnyRunningTaskIn(tasks)) {
            String message = String.format("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다. hostId = %d, jobId = %d", hostId,
                    jobId);
            throw new BusinessException(message, ErrorCode.T001);
        }
        runningTaskRepository.saveAll(tasks.createRunningTasks());
    }

    public JobActiveResponse isJobActivated(final Long hostId, final Long jobId) {
        Tasks tasks = findTasksByHostIdAndJobId(hostId, jobId);
        return JobActiveResponse.from(existsAnyRunningTaskIn(tasks));
    }

    public SseEmitter connectRunningTasks(final Long hostId, final Long jobId) {
        RunningTasksResponse runningTasks = findExistingRunningTasks(hostId, jobId);
        return runningTaskSseEmitterContainer.createEmitterWithConnectionEvent(jobId, runningTasks);
    }

    @Transactional
    public void flipRunningTask(Long taskId) {
        Task task = taskRepository.getById(taskId);
        RunningTask runningTask = task.getRunningTask();
        if (runningTask == null) {
            String message = String.format("현재 진행 중인 작업이 아닙니다. taskId = %d", taskId);
            throw new BusinessException(message, ErrorCode.R002);
        }
        runningTask.flipCheckedStatus();
    }

    // runningTaskRepository.existsByTaskIdIn() 메서드, Lock 거는 조회 분리되면 readOnly transaction으로 변경해야함.
    @Transactional
    public RunningTasksResponse showRunningTasks(final Long jobId) {
        Job job = jobRepository.getById(jobId);
        Tasks tasks = new Tasks(taskRepository.findAllBySectionJob(job));

        if (!existsAnyRunningTaskIn(tasks)) {
            String message = String.format("현재 진행중인 RunningTask가 없습니다. jobId = %d", jobId);
            throw new BusinessException(message, ErrorCode.R001);
        }
        return RunningTasksResponse.from(tasks);
    }

    @Transactional
    public void flipRunningTask(final Long hostId, final Long taskId) {
        Host host = hostRepository.getById(hostId);
        Task task = taskRepository.getBySectionJobSpaceHostAndId(host, taskId);
        RunningTask runningTask = task.getRunningTask();
        if (runningTask == null) {
            String message = String.format("현재 진행 중인 작업이 아닙니다. hostId = %d, taskId = %d", hostId, taskId);
            throw new BusinessException(message, ErrorCode.R002);
        }

        runningTask.flipCheckedStatus();
        Long jobId = task.getSection().getJob().getId();
        RunningTasksResponse runningTasks = findExistingRunningTasks(hostId, jobId);

        runningTaskSseEmitterContainer.publishFlipEvent(jobId, runningTasks);
    }

    public TasksResponse findTasks(final Long hostId, final Long jobId) {
        Tasks tasks = findTasksByHostIdAndJobId(hostId, jobId);
        return TasksResponse.from(tasks);
    }

    @Transactional
    public void checkRunningTasksInSection(final Long sectionId) {
        Section section = sectionRepository.getById(sectionId);
        Tasks tasks = new Tasks(taskRepository.findAllBySection(section));

        if (!existsAnyRunningTaskIn(tasks)) {
            String message = String.format("현재 진행중인 RunningTask가 없습니다, sectionId = %d", sectionId);
            throw new BusinessException(message, ErrorCode.R002);
        }
        RunningTasks runningTasks = tasks.getRunningTasks();
        runningTasks.check();
    }

    private RunningTasksResponse findExistingRunningTasks(final Long hostId, final Long jobId) {
        Tasks tasks = findTasksByHostIdAndJobId(hostId, jobId);
        if (!existsAnyRunningTaskIn(tasks)) {
            String message = String.format("현재 진행중인 RunningTask가 없습니다 hostId = %d, jobId = %d", hostId, jobId);
            throw new BusinessException(message, ErrorCode.R001);
        }
        return RunningTasksResponse.from(tasks);
    }

    private Tasks findTasksByHostIdAndJobId(final Long hostId, final Long jobId) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        return new Tasks(taskRepository.findAllBySectionJob(job));
    }

    private boolean existsAnyRunningTaskIn(final Tasks tasks) {
        return runningTaskRepository.existsByTaskIdIn(tasks.getTaskIds());
    }
}
