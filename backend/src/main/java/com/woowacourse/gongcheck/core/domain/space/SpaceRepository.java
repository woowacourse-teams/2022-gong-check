package com.woowacourse.gongcheck.core.domain.space;

import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.vo.Name;
import com.woowacourse.gongcheck.exception.ErrorCode;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    List<Space> findAllByHost(final Host host);

    Optional<Space> findByHostAndId(final Host host, final Long id);

    boolean existsByHostAndName(final Host host, final Name name);

    boolean existsByHostAndNameAndIdNot(final Host host, final Name name, final Long id);

    default Space getByHostAndId(final Host host, final Long id) throws NotFoundException {
        return findByHostAndId(host, id)
                .orElseThrow(() -> {
                    String message = String.format("존재하지 않는 공간입니다. hostId = %d, spaceId = %d", host.getId(), id);
                    throw new NotFoundException(message, ErrorCode.SP04);
                });
    }
}
