package com.woowacourse.gongcheck.core.application;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.core.application.response.SpaceResponse;
import com.woowacourse.gongcheck.core.application.response.SpacesResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.job.JobRepository;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.section.SectionRepository;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.core.domain.task.RunningTaskRepository;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.TaskRepository;
import com.woowacourse.gongcheck.core.presentation.request.SpaceChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.SpaceCreateRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
import java.util.List;
import java.util.Objects;
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

    public SpacesResponse findSpaces(final Long hostId) {
        Host host = hostRepository.getById(hostId);
        List<Space> spaces = spaceRepository.findAllByHost(host);
        return SpacesResponse.from(spaces);
    }

    @Transactional
    public Long createSpace(final Long hostId, final SpaceCreateRequest request) {
        Host host = hostRepository.getById(hostId);
        checkDuplicateSpaceName(request.getName(), host);

        String imageUrl = uploadImageAndGetUrlOrNull(request.getImage());

        Space space = Space.builder()
                .host(host)
                .name(request.getName())
                .imageUrl(imageUrl)
                .build();
        return spaceRepository.save(space)
                .getId();
    }

    public SpaceResponse findSpace(final Long hostId, final Long spaceId) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
        return SpaceResponse.from(space);
    }

    @Transactional
    public void changeSpace(final Long hostId, final Long spaceId, final SpaceChangeRequest request,
                            final MultipartFile image) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
    }

    @Transactional
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

    private void checkDuplicateSpaceName(final String spaceName, final Host host) {
        if (spaceRepository.existsByHostAndName(host, spaceName)) {
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
