package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.core.application.response.SubmissionCreatedResponse;
import com.woowacourse.gongcheck.core.application.response.SubmissionsResponse;
import com.woowacourse.gongcheck.core.application.support.LoggingFormatConverter;
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
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    private final NotificationService notificationService;

    public SubmissionService(final HostRepository hostRepository, final JobRepository jobRepository,
                             final SpaceRepository spaceRepository, final TaskRepository taskRepository,
                             final RunningTaskRepository runningTaskRepository,
                             final SubmissionRepository submissionRepository,
                             final NotificationService notificationService) {
        this.hostRepository = hostRepository;
        this.jobRepository = jobRepository;
        this.spaceRepository = spaceRepository;
        this.taskRepository = taskRepository;
        this.runningTaskRepository = runningTaskRepository;
        this.submissionRepository = submissionRepository;
        this.notificationService = notificationService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void submitJobCompletion(final Long hostId, final Long jobId,
                                    final SubmissionRequest request) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        saveSubmissionAndClearRunningTasks(request, job);
        if (job.hasUrl()) {
            sendNotification(request, job);
        }
    }

    private void sendNotification(final SubmissionRequest request, final Job job) {
        notificationService.sendMessage(SubmissionCreatedResponse.of(request.getAuthor(), job));
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
        List<Long> taskIds = tasks.getTaskIds();
        if (!runningTaskRepository.existsByTaskIdIn(taskIds)) {
            String message = String.format("현재 제출할 수 있는 진행중인 작업이 존재하지 않습니다. taskIds = %s",
                    LoggingFormatConverter.convertIdsToString(taskIds));
            throw new BusinessException(message, ErrorCode.S001);
        }
    }
}
