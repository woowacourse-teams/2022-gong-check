package com.woowacourse.gongcheck.domain.host;

import com.woowacourse.gongcheck.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {

    default Host getById(Long hostId) throws NotFoundException {
        return findById(hostId).orElseThrow(() -> new NotFoundException("존재하지 않는 호스트입니다."));
    }
}
