package com.woowacourse.gongcheck.domain.space;

import com.woowacourse.gongcheck.domain.host.Host;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    Slice<Space> findByHost(final Host host, final Pageable pageable);

    Optional<Space> findByHostAndId(final Host host, final Long id);
}
