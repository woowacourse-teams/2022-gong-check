package com.woowacourse.gongcheck.core.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.core.domain.host.SpacePassword;
import com.woowacourse.gongcheck.core.presentation.request.SpacePasswordChangeRequest;
import com.woowacourse.gongcheck.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        String changedPassword = "4567";
        hostService.changeSpacePassword(host.getId(), new SpacePasswordChangeRequest(changedPassword));

        assertThat(hostRepository.getById(host.getId()).getSpacePassword())
                .isEqualTo(new SpacePassword(changedPassword));
    }

    @Test
    void 존재하지_않는_Host의_SpacePassword를_변경하려는_경우_예외가_발생한다() {
        SpacePasswordChangeRequest request = new SpacePasswordChangeRequest("1234");
        assertThatThrownBy(() -> hostService.changeSpacePassword(0L, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Nested
    class Host_아이디_조회 {

        private Host host;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1111L));
        }

        @Test
        void 정상적으로_조회한다() {
            Long hostId = host.getId();
            Long result = hostService.getHostId(hostId);
            assertThat(result).isEqualTo(hostId);
        }

        @Test
        void Host가_존재하지_않는_경우_예외를_발생시킨다() {
            assertThatThrownBy(() -> hostService.getHostId(0L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 호스트입니다.");
        }
    }
}
