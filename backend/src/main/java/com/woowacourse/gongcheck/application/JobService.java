package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.application.response.JobsResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.job.JobRepository;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.domain.task.Task;
import com.woowacourse.gongcheck.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SectionRequest;
import com.woowacourse.gongcheck.presentation.request.TaskRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

    public JobService(final HostRepository hostRepository, final SpaceRepository spaceRepository,
                      final JobRepository jobRepository) {
        this.hostRepository = hostRepository;
        this.spaceRepository = spaceRepository;
        this.jobRepository = jobRepository;
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

        Job job = Job.builder()
                .space(space)
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();
        List<Section> sections = createSections(request.getSections(), job);
        job.addAllSections(sections);
        jobRepository.save(job);
        return job.getId();
    }

    private List<Section> createSections(final List<SectionRequest> sections, final Job job) {
        return sections.stream()
                .map(i -> createSection(i, job))
                .collect(Collectors.toList());
    }

    private Section createSection(final SectionRequest sectionRequest, final Job job) {
        Section section = Section.builder()
                .job(job)
                .name(sectionRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();
        List<Task> tasks = createTasks(sectionRequest.getTasks(), section);
        section.addAllTasks(tasks);
        return section;
    }

    private List<Task> createTasks(final List<TaskRequest> taskRequests, final Section section) {
        return taskRequests.stream()
                .map(i -> createTask(i, section))
                .collect(Collectors.toList());
    }

    private Task createTask(final TaskRequest taskRequest, final Section section) {
        return Task.builder()
                .section(section)
                .name(taskRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
