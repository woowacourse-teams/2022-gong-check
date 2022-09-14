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
import com.woowacourse.gongcheck.core.domain.vo.Name;
import com.woowacourse.gongcheck.core.presentation.request.SpaceChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.SpaceCreateRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SpaceService {

    private final HostRepository hostRepository;
    private final SpaceRepository spaceRepository;
    private final JobRepository jobRepository;
    private final SectionRepository sectionRepository;
    private final TaskRepository taskRepository;
    private final RunningTaskRepository runningTaskRepository;

    public SpaceService(final HostRepository hostRepository, final SpaceRepository spaceRepository,
                        final JobRepository jobRepository, final SectionRepository sectionRepository,
                        final TaskRepository taskRepository, final RunningTaskRepository runningTaskRepository) {
        this.hostRepository = hostRepository;
        this.spaceRepository = spaceRepository;
        this.jobRepository = jobRepository;
        this.sectionRepository = sectionRepository;
        this.taskRepository = taskRepository;
        this.runningTaskRepository = runningTaskRepository;
    }

    public SpaceResponse findSpace(final Long hostId, final Long spaceId) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
        return SpaceResponse.from(space);
    }

    public SpacesResponse findSpaces(final Long hostId) {
        Host host = hostRepository.getById(hostId);
        List<Space> spaces = spaceRepository.findAllByHost(host);
        return SpacesResponse.from(spaces);
    }

    @Transactional
    public Long createSpace(final Long hostId, final SpaceCreateRequest request) {
        Host host = hostRepository.getById(hostId);
        Name spaceName = new Name(request.getName());
        checkDuplicateSpaceName(spaceName, host);

        Space space = Space.builder()
                .host(host)
                .name(spaceName)
                .imageUrl(request.getImageUrl())
                .build();
        return spaceRepository.save(space)
                .getId();
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

    @Transactional
    public void changeSpace(final Long hostId, final Long spaceId, final SpaceChangeRequest request) {
        Host host = hostRepository.getById(hostId);
        Space space = spaceRepository.getByHostAndId(host, spaceId);
        Name changeName = new Name(request.getName());
        checkDuplicateSpaceName(changeName, host, space);

        space.changeName(changeName);
        space.changeImageUrl(request.getImageUrl());
    }

    private void checkDuplicateSpaceName(final Name spaceName, final Host host) {
        if (spaceRepository.existsByHostAndName(host, spaceName)) {
            String message = String.format("이미 존재하는 이름입니다. hostId = %d, spaceName = %s", host.getId(),
                    spaceName.getValue());
            throw new BusinessException(message, ErrorCode.SP01);
        }
    }

    private void checkDuplicateSpaceName(final Name spaceName, final Host host, final Space space) {
        if (spaceRepository.existsByHostAndNameAndIdNot(host, spaceName, space.getId())) {
            String message = String.format("이미 존재하는 이름입니다. hostId = %d, spaceName = %s, spaceId = %d", host.getId(),
                    spaceName.getValue(), space.getId());
            throw new BusinessException(message, ErrorCode.SP02);
        }
    }
}
