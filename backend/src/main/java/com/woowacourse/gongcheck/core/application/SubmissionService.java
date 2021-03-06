package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.core.application.response.SubmissionCreatedResponse;
import com.woowacourse.gongcheck.core.application.response.SubmissionsResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.core.domain.submission.Submission;
import com.woowacourse.gongcheck.core.domain.submission.SubmissionRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTasks;
import com.woowacourse.gongcheck.core.domain.task.TaskRepository;
import com.woowacourse.gongcheck.core.domain.task.Tasks;
import com.woowacourse.gongcheck.core.presentation.request.SubmissionRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
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
    public SubmissionCreatedResponse submitJobCompletion(final Long hostId, final Long jobId,
                                                         final SubmissionRequest request) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        saveSubmissionAndClearRunningTasks(request, job);
        return SubmissionCreatedResponse.of(request.getAuthor(), job);
    }

    public SubmissionsResponse findPage(final Long hostId, final Long spaceId, final Pageable pageable) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
        List<Job> jobs = jobRepository.findAllBySpace(space);
        Slice<Submission> submissions = submissionRepository.findAllByJobIn(jobs, pageable);
        return SubmissionsResponse.from(submissions);
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
            throw new BusinessException("?????? ????????? ??? ?????? ???????????? ????????? ???????????? ????????????.");
        }
    }
}
