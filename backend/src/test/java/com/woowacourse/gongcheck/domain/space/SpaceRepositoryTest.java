package com.woowacourse.gongcheck.domain.space;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
class SpaceRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Test
    void 멤버아이디로_공간을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        Space space1 = Space_생성(host, "잠실");
        Space space2 = Space_생성(host, "선릉");
        Space space3 = Space_생성(host, "양평같은방");
        spaceRepository.saveAll(List.of(space1, space2, space3));

        Slice<Space> result = spaceRepository.findByHost(host, PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getSize()).isEqualTo(2),
                () -> assertThat(result.hasNext()).isTrue()
        );
    }

    @Test
    void 멤버와_아이디로_공간을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = spaceRepository.save(Space_생성(host, "잠실"));

        Space result = spaceRepository.getByHostAndId(host, space.getId());

        assertThat(result).isEqualTo(space);
    }

    @Test
    void 다른_호스트의_공간을_조회할_경우_예외가_발생한다() {
        Host host1 = hostRepository.save(Host_생성("1234"));
        Host host2 = hostRepository.save(Host_생성("1234"));
        Space space = Space_생성(host2, "잠실");
        spaceRepository.save(space);

        assertThatThrownBy(() ->
                spaceRepository.getByHostAndId(host1, space.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 공간입니다.");
    }

    @Test
    void 호스트와_공간_이름을_입력_받아_이미_존재하는_공간_이름이면_참을_반환한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        Space space = Space_생성(host, "잠실");
        spaceRepository.save(space);

        boolean result = spaceRepository.existsByHostAndName(host, space.getName());

        assertThat(result).isTrue();
    }

    @Test
    void 호스트와_공간_이름을_입력_받아_이미_존재하는_공간_이름이_아니면_거짓을_반환한다() {
        Host host = hostRepository.save(Host_생성("1234"));

        boolean result = spaceRepository.existsByHostAndName(host, "잠실");

        assertThat(result).isFalse();
    }
}
