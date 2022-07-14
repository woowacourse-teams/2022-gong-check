package com.woowacourse.gongcheck.domain.space;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    Slice<Space> findByHost(final Host host, final Pageable pageable);

    Optional<Space> findByHostAndId(final Host host, final Long id);

    default Space getByHostAndId(final Host host, final Long id) throws NotFoundException {
        return findByHostAndId(host, id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 공간입니다."));
    }

    boolean existsByHostAndName(final Host host, final String name);
}
