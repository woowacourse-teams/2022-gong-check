package com.woowacourse.gongcheck.domain.host;

import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {

    Optional<Host> findByGithubId(final Long githubId);

    boolean existsByGithubId(final Long githubId);

    default Host getById(final Long id) throws NotFoundException {
        return findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
    }

    default Host getByGithubId(final Long githubId) throws NotFoundException {
        return findByGithubId(githubId).orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
    }
}
