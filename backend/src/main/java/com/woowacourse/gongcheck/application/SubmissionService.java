package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.submission.SubmissionRepository;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.domain.task.RunningTasks;
import com.woowacourse.gongcheck.domain.task.TaskRepository;
import com.woowacourse.gongcheck.domain.task.Tasks;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SubmissionService {

    private final HostRepository hostRepository;
    private final JobRepository jobRepository;
    private final TaskRepository taskRepository;
    private final RunningTaskRepository runningTaskRepository;
    private final SubmissionRepository submissionRepository;

    public SubmissionService(final HostRepository hostRepository, final JobRepository jobRepository,
                             final TaskRepository taskRepository,
                             final RunningTaskRepository runningTaskRepository,
                             final SubmissionRepository submissionRepository) {
        this.hostRepository = hostRepository;
        this.jobRepository = jobRepository;
        this.taskRepository = taskRepository;
        this.runningTaskRepository = runningTaskRepository;
        this.submissionRepository = submissionRepository;
    }

    @Transactional
    public void submitJobCompletion(final Long hostId, final Long jobId, final SubmissionRequest request) {
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
        Job job = jobRepository.findBySpaceHostAndId(host, jobId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 작업입니다."));
        saveSubmissionAndClearRunningTasks(request, job);
    }

    private void saveSubmissionAndClearRunningTasks(final SubmissionRequest request, final Job job) {
        Tasks tasks = new Tasks(taskRepository.findAllBySectionJob(job));
        RunningTasks runningTasks = new RunningTasks(runningTaskRepository.findAllById(tasks.getTaskIds()));
        validateRunning(tasks);
        validateCompletion(runningTasks);
        submissionRepository.save(job.createSubmission(request.getAuthor()));
        runningTaskRepository.deleteAllByIdInBatch(tasks.getTaskIds());
    }

    private void validateRunning(final Tasks tasks) {
        if (!runningTaskRepository.existsByTaskIdIn(tasks.getTaskIds())) {
            throw new BusinessException("현재 제출할 수 있는 진행중인 작업이 존재하지 않습니다.");
        }
    }

    private void validateCompletion(final RunningTasks runningTasks) {
        if (!runningTasks.isAllChecked()) {
            throw new BusinessException("모든 작업이 완료되지않아 제출이 불가합니다.");
        }
    }
}
