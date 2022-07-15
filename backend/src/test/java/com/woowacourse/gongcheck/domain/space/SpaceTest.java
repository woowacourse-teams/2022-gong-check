package com.woowacourse.gongcheck.domain.space;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.domain.host.Host;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class SpaceTest {

    @Test
    void Space_이름은_20자_이하여야_한다() {
        Host host = Host_생성("1234");

        assertThatThrownBy(
                () -> Space.builder()
                        .host(host)
                        .name("123456789123467891234")
                        .createdAt(LocalDateTime.now())
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공간의 이름은 20자 이하여야합니다.");
    }

    @Test
    void Space_이름은_빈_값일_수_없다() {
        Host host = Host_생성("1234");

        assertThatThrownBy(
                () -> Space.builder()
                        .host(host)
                        .name("")
                        .createdAt(LocalDateTime.now())
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공간의 이름은 공백일 수 없습니다.");
    }
}