package com.woowacourse.gongcheck.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.application.response.JobsResponse;
import com.woowacourse.gongcheck.application.response.SlackUrlResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.section.SectionRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.domain.task.Task;
import com.woowacourse.gongcheck.domain.task.TaskRepository;
import com.woowacourse.gongcheck.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SlackUrlChangeRequest;
import com.woowacourse.gongcheck.presentation.request.TaskCreateRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JobService {

    private final HostRepository hostRepository;
    private final SpaceRepository spaceRepository;
    private final JobRepository jobRepository;
    private final SectionRepository sectionRepository;
    private final TaskRepository taskRepository;
    private final RunningTaskRepository runningTaskRepository;

    public JobService(final HostRepository hostRepository, final SpaceRepository spaceRepository,
                      final JobRepository jobRepository, final SectionRepository sectionRepository,
                      final TaskRepository taskRepository, final RunningTaskRepository runningTaskRepository) {
        this.hostRepository = hostRepository;
        this.spaceRepository = spaceRepository;
        this.jobRepository = jobRepository;
        this.sectionRepository = sectionRepository;
        this.taskRepository = taskRepository;
        this.runningTaskRepository = runningTaskRepository;
    }

    public JobsResponse findPage(final Long hostId, final Long spaceId, final Pageable pageable) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
        Slice<Job> jobs = jobRepository.findBySpaceHostAndSpace(host, space, pageable);
        return JobsResponse.from(jobs);
    }

    @Transactional
    public Long createJob(final Long hostId, final Long spaceId, final JobCreateRequest request) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
        return saveJob(request, space);
    }

    @Transactional
    public void removeJob(final Long hostId, final Long jobId) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        List<Section> sections = sectionRepository.findAllByJob(job);
        List<Task> tasks = taskRepository.findAllBySectionIn(sections);

        deleteJob(jobId, sections, tasks);
    }

    @Transactional
    public Long updateJob(final Long hostId, final Long jobId, final JobCreateRequest request) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        Space space = job.getSpace();
        List<Section> sections = sectionRepository.findAllByJob(job);
        List<Task> tasks = taskRepository.findAllBySectionIn(sections);

        deleteJob(jobId, sections, tasks);
        return saveJob(request, space);
    }

    public SlackUrlResponse findSlackUrl(final Long hostId, final Long jobId) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        return SlackUrlResponse.from(job);
    }

    @Transactional
    public void changeSlackUrl(final Long hostId, final Long jobId, final SlackUrlChangeRequest request) {
        Host host = hostRepository.getById(hostId);
        Job job = jobRepository.getBySpaceHostAndId(host, jobId);
        job.changeSlackUrl(request.getSlackUrl());
    }

    private Long saveJob(final JobCreateRequest request, final Space space) {
        Job job = Job.builder()
                .space(space)
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();
        jobRepository.save(job);
        createSectionsAndTasks(request.getSections(), job);
        return job.getId();
    }

    private void createSectionsAndTasks(final List<SectionCreateRequest> sectionCreateRequests, final Job job) {
        sectionCreateRequests.forEach(sectionRequest -> createSectionAndTasks(sectionRequest, job));
    }

    private Section createSectionAndTasks(final SectionCreateRequest sectionCreateRequest, final Job job) {
        Section section = Section.builder()
                .job(job)
                .name(sectionCreateRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();
        sectionRepository.save(section);
        createTasks(sectionCreateRequest.getTasks(), section);
        return section;
    }

    private void createTasks(final List<TaskCreateRequest> taskCreateRequests, final Section section) {
        List<Task> tasks = taskCreateRequests
                .stream()
                .map(taskRequest -> createTask(taskRequest, section))
                .collect(toList());
        taskRepository.saveAll(tasks);
    }

    private Task createTask(final TaskCreateRequest taskCreateRequest, final Section section) {
        return Task.builder()
                .section(section)
                .name(taskCreateRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private void deleteJob(final Long jobId, final List<Section> sections, final List<Task> tasks) {
        runningTaskRepository.deleteAllByIdInBatch(tasks.stream()
                .map(Task::getId)
                .collect(toList()));
        taskRepository.deleteAllInBatch(tasks);
        sectionRepository.deleteAllInBatch(sections);
        jobRepository.deleteById(jobId);
    }
}
