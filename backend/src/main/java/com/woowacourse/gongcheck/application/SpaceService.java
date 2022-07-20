package com.woowacourse.gongcheck.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
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
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.presentation.request.SpaceCreateRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class SpaceService {

    private final HostRepository hostRepository;
    private final SpaceRepository spaceRepository;
    private final JobRepository jobRepository;
    private final SectionRepository sectionRepository;
    private final TaskRepository taskRepository;
    private final RunningTaskRepository runningTaskRepository;
    private final ImageUploader imageUploader;

    public SpaceService(final HostRepository hostRepository, final SpaceRepository spaceRepository,
                        final JobRepository jobRepository, final SectionRepository sectionRepository,
                        final TaskRepository taskRepository, final RunningTaskRepository runningTaskRepository,
                        final ImageUploader imageUploader) {
        this.hostRepository = hostRepository;
        this.spaceRepository = spaceRepository;
        this.jobRepository = jobRepository;
        this.sectionRepository = sectionRepository;
        this.taskRepository = taskRepository;
        this.runningTaskRepository = runningTaskRepository;
        this.imageUploader = imageUploader;
    }

    public SpacesResponse findPage(final Long hostId, final Pageable pageable) {
        Host host = hostRepository.getById(hostId);
        Slice<Space> spaces = spaceRepository.findByHost(host, pageable);
        return SpacesResponse.from(spaces);
    }

    public Long createSpace(final Long hostId, final SpaceCreateRequest request) {
        Host host = hostRepository.getById(hostId);
        checkDuplicateName(request, host);

        String imageUrl = uploadImageAndGetUrlOrNull(request.getImage());

        Space space = Space.builder()
                .host(host)
                .name(request.getName())
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build();
        return spaceRepository.save(space)
                .getId();
    }

    public void removeSpace(final Long hostId, final Long spaceId) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
        List<Job> jobs = jobRepository.findAllBySpace(space);
        List<Section> sections = sectionRepository.findAllByJobIn(jobs);
        List<Task> tasks = taskRepository.findAllBySectionIn(sections);

        runningTaskRepository.deleteAllByIdInBatch(tasks.stream()
                .map(Task::getId)
                .collect(toList()));
        taskRepository.deleteAllInBatch(tasks);
        sectionRepository.deleteAllInBatch(sections);
        jobRepository.deleteAllInBatch(jobs);
        spaceRepository.deleteById(spaceId);
    }

    private void checkDuplicateName(final SpaceCreateRequest request, final Host host) {
        if (spaceRepository.existsByHostAndName(host, request.getName())) {
            throw new BusinessException("이미 존재하는 이름입니다.");
        }
    }

    private String uploadImageAndGetUrlOrNull(final MultipartFile image) {
        if (!Objects.isNull(image)) {
            return imageUploader.upload(image, "spaces");
        }
        return null;
    }
}
