package com.woowacourse.gongcheck.domain.host;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class HostRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Test
    void 아이디로_호스트를_조회한다() {
        Host host = Host_생성("1234");
        Host savedHost = hostRepository.save(host);

        Host findHost = hostRepository.getById(savedHost.getId());

        assertThat(findHost).isEqualTo(savedHost);
    }

    @Test
    void 입력받은_아이디와_일치하는_호스트가_없으면_예외를_던진다() {
        assertThatThrownBy(() -> hostRepository.getById(0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }
}
