package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.fixture.FixtureFactory;
import com.woowacourse.gongcheck.presentation.request.SpacePasswordChangeRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class HostServiceTest {

    @Autowired
    private HostService hostService;

    @Autowired
    private HostRepository hostRepository;

    @Test
    void SpacePassword를_변경한다() {
        Host host = hostRepository.save(FixtureFactory.Host_생성("1234"));

        String changedPassword = "4567";
        hostService.changeSpacePassword(host.getId(), new SpacePasswordChangeRequest(changedPassword));

        Assertions.assertThat(hostRepository.getById(host.getId()).getSpacePassword())
                .isEqualTo(changedPassword);
    }

    @Test
    void 존재하지_않는_Host의_SpacePassword를_변경하려는_경우_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> hostService.changeSpacePassword(0L, new SpacePasswordChangeRequest("1234")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }
}
