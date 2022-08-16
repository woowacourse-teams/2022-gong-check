package com.woowacourse.gongcheck.core.domain.host;

import com.woowacourse.gongcheck.exception.ErrorCode;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {

    Optional<Host> findByGithubId(final Long githubId);

    boolean existsByGithubId(final Long githubId);

    default Host getById(final Long id) throws NotFoundException {
        return findById(id).orElseThrow(() -> {
            String message = String.format("존재하지 않는 호스트입니다. hostId = %d", id);
            throw new NotFoundException(message, ErrorCode.H004);
        });
    }

    default Host getByGithubId(final Long githubId) throws NotFoundException {
        return findByGithubId(githubId).orElseThrow(() -> {
            String message = String.format("존재하지 않는 호스트입니다. githubId = %d", githubId);
            throw new NotFoundException(message, ErrorCode.H005);
        });
    }
}
