package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Member_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.member.MemberRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SpaceServiceTest {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Test
    void 공간을_조회한다() {
        Member host = memberRepository.save(Member_생성("1234"));
        Space space1 = Space_생성(host, "test1");
        Space space2 = Space_생성(host, "test2");
        Space space3 = Space_생성(host, "test3");
        spaceRepository.saveAll(List.of(space1, space2, space3));

        SpacesResponse result = spaceService.findPage(host.getId(), PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getSpaces()).hasSize(2),
                () -> assertThat(result.isHasNext()).isTrue()
        );
    }

    @Test
    void 존재하지_않는_호스트로_공간을_조회할_경우_예외를_던진다() {
        assertThatThrownBy(() -> spaceService.findPage(0L, PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }
}
