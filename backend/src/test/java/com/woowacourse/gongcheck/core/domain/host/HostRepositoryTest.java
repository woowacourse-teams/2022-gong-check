package com.woowacourse.gongcheck.core.domain.host;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.config.JpaConfig;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
@DisplayName("HostRepository 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HostRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Nested
    class save_메소드는 {

        @Nested
        class 저장할_호스트를_받는_경우 {

            private Host host;

            @BeforeEach
            void setUp() {
                host = Host_생성("1234", 1234567L);
            }

            @Test
            void 생성시간이_같이_저장된다() {
                LocalDateTime nowLocalDateTime = LocalDateTime.now();
                Host actual = hostRepository.save(host);

                assertThat(actual.getCreatedAt()).isAfter(nowLocalDateTime);
            }
        }
    }

    @Nested
    class getById_메소드는 {

        @Nested
        class 존재하는_Host의_id를_입력받는_경우 {

            private Long hostId;

            @BeforeEach
            void setUp() {
                hostId = hostRepository.save(Host_생성("1234", 1234567L))
                        .getId();
            }

            @Test
            void Host를_반환한다() {
                Host actual = hostRepository.getById(hostId);
                assertThat(actual.getId()).isEqualTo(hostId);
            }
        }

        @Nested
        class 존재하지않는_Host의_id를_입력받는_경우 {

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> hostRepository.getById(0L))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }
    }

    @Nested
    class getByGithubId_메소드는 {

        @Nested
        class 존재하는_Host의_githubId를_입력받는_경우 {

            private final Long githubId = 1234567L;
            private Host host;

            @BeforeEach
            void setUp() {
                host = hostRepository.save(Host_생성("1234", githubId));
            }

            @Test
            void Host를_반환한다() {
                Host actual = hostRepository.getByGithubId(githubId);
                assertThat(actual).isEqualTo(host);
            }
        }

        @Nested
        class 존재하지않는_Host의_githubId를_입력받는_경우 {

            private final Long errorGithubId = 987654L;

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> hostRepository.getByGithubId(errorGithubId))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage("존재하지 않는 호스트입니다.");
            }
        }
    }

    @Nested
    class existsByGithubId_메소드는 {

        @Nested
        class 존재하는_Host의_githubId를_입력받는_경우 {

            private final Long githubId = 1234567L;

            @BeforeEach
            void setUp() {
                hostRepository.save(Host_생성("1234", githubId));
            }

            @Test
            void True를_반환한다() {
                boolean actual = hostRepository.existsByGithubId(githubId);
                assertThat(actual).isTrue();
            }
        }

        @Nested
        class 존재하지않는_Host의_githubId를_입력받는_경우 {

            @Test
            void False를_발환한다() {
                boolean actual = hostRepository.existsByGithubId(987654L);
                assertThat(actual).isFalse();
            }
        }
    }
}
