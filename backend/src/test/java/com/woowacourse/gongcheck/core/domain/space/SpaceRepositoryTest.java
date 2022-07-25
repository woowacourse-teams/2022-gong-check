package com.woowacourse.gongcheck.core.domain.space;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
class SpaceRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Test
    void Space_저장_시_생성시간이_저장된다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        Space space = spaceRepository.save(Space.builder()
                .host(host)
                .name("잠실")
                .build());
        assertThat(space.getCreatedAt()).isAfter(nowLocalDateTime);
    }

    @Test
    void Host의_Space를_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        Space space1 = Space_생성(host, "잠실 캠퍼스");
        Space space2 = Space_생성(host, "선릉 캠퍼스");
        Space space3 = Space_생성(host, "양평같은방");
        spaceRepository.saveAll(List.of(space1, space2, space3));

        List<Space> result = spaceRepository.findAllByHost(host);

        assertThat(result).hasSize(3);
    }

    @Nested
    class Space를_조회할_때 {

        private Host host;
        private Space space;

        @BeforeEach
        void setUp() {
            host = hostRepository.save(Host_생성("1234", 1234L));
            space = spaceRepository.save(Space_생성(host, "잠실 캠퍼스"));
        }

        @Test
        void Host와_SpaceId로_조회한다() {
            Space result = spaceRepository.getByHostAndId(host, space.getId());

            assertThat(result).isEqualTo(space);
        }

        @Test
        void 다른_Host의_Space를_조회할_경우_예외가_발생한다() {
            Host anotherHost = hostRepository.save(Host_생성("1234", 2345L));

            assertThatThrownBy(() ->
                    spaceRepository.getByHostAndId(anotherHost, space.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 공간입니다.");
        }

        @Test
        void 존재하지_않는_Space를_조회할_경우_예외가_발생한다() {
            assertThatThrownBy(() ->
                    spaceRepository.getByHostAndId(host, 0L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("존재하지 않는 공간입니다.");
        }
    }

    @ParameterizedTest
    @CsvSource(value = {"잠실 캠퍼스, true", "선릉 캠퍼스, false"})
    void 이미_존재하는_Space이름인지_확인한다(final String spaceName, final boolean expected) {
        Host host = hostRepository.save(Host_생성("1234", 1234L));
        spaceRepository.save(Space_생성(host, "잠실 캠퍼스"));

        boolean actual = spaceRepository.existsByHostAndName(host, spaceName);

        assertThat(actual).isEqualTo(expected);
    }
}
