package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.JobSubmissionsResponse;
import com.woowacourse.gongcheck.application.response.SubmissionResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.domain.submission.Submission;
import com.woowacourse.gongcheck.domain.submission.SubmissionRepository;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.domain.task.RunningTasks;
import com.woowacourse.gongcheck.domain.task.TaskRepository;
import com.woowacourse.gongcheck.domain.task.Tasks;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SubmissionService {

    private final HostRepository hostRepository;
    private final JobRepository jobRepository;
    private final SpaceRepository spaceRepository;
    private final TaskRepository taskRepository;
    private final RunningTaskRepository runningTaskRepository;
    private final SubmissionRepository submissionRepository;

    public SubmissionService(final HostRepository hostRepository, final JobRepository jobRepository,
                             final SpaceRepository spaceRepository,
                             final TaskRepository taskRepository, final RunningTaskRepository runningTaskRepository,
                             final SubmissionRepository submissionRepository) {
        this.hostRepository = hostRepository;
        this.jobRepository = jobRepository;
        this.spaceRepository = spaceRepository;
        this.taskRepository = taskRepository;
        this.runningTaskRepository = runningTaskRepository;
        this.submissionRepository = submissionRepository;
    }

    @Transactional
    public SubmissionResponse submitJobCompletion(final Long hostId, final Long jobId,
                                                  final SubmissionRequest request) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        saveSubmissionAndClearRunningTasks(request, job);
        return SubmissionResponse.of(request.getAuthor(), job);
    }

    private void saveSubmissionAndClearRunningTasks(final SubmissionRequest request, final Job job) {
        Tasks tasks = new Tasks(taskRepository.findAllBySectionJob(job));
        validateRunning(tasks);
        RunningTasks runningTasks = new RunningTasks(runningTaskRepository.findAllById(tasks.getTaskIds()));
        runningTasks.validateCompletion();
        submissionRepository.save(job.createSubmission(request.getAuthor()));
        runningTaskRepository.deleteAllByIdInBatch(tasks.getTaskIds());
    }

    private void validateRunning(final Tasks tasks) {
        if (!runningTaskRepository.existsByTaskIdIn(tasks.getTaskIds())) {
            throw new BusinessException("현재 제출할 수 있는 진행중인 작업이 존재하지 않습니다.");
        }
    }

    public JobSubmissionsResponse findPage(final Long hostId, final Long spaceId, final Pageable pageable) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
        List<Job> jobs = jobRepository.findAllBySpace(space);
        Slice<Submission> submissions = submissionRepository.findAllByJobIn(jobs, pageable);
        return JobSubmissionsResponse.from(submissions);
    }
}
