package com.woowacourse.gongcheck.domain.host;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
class HostRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Test
    void 호스트_저장_시_생성시간이_저장된다() {
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        Host host = hostRepository.save(Host.builder()
                .githubId(1L)
                .imageUrl("http://image.com")
                .build());
        assertThat(host.getCreatedAt()).isAfter(nowLocalDateTime);
    }

    @Test
    void 아이디로_호스트를_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        Host result = hostRepository.getById(host.getId());

        assertThat(result).isEqualTo(host);
    }

    @Test
    void 입력받은_아이디와_일치하는_호스트가_없으면_예외를_던진다() {
        assertThatThrownBy(() -> hostRepository.getById(0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 깃허브_아이디로_호스트를_조회한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        Host result = hostRepository.getByGithubId(1234L);

        assertThat(result).isEqualTo(host);
    }

    @Test
    void 깃허브_아이디로_조회시_해당하는_호스트가_없으면_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        assertThatThrownBy(() -> hostRepository.getByGithubId(1235L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"1234, true", "1235, false"})
    void 깃허브_아이디로_호스트가_존재하는지_확인한다(final Long githubId, final boolean actual) {
        Host host = hostRepository.save(Host_생성("1234", 1234L));

        boolean result = hostRepository.existsByGithubId(githubId);

        assertThat(result).isEqualTo(actual);
    }
}
