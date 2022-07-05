package com.woowacourse.gongcheck.domain.space;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Member_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.member.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
class SpaceRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Test
    void 멤버아이디로_공간을_조회한다() {
        Member host = memberRepository.save(Member_생성("1234"));
        Space space1 = Space_생성(host, "test1");
        Space space2 = Space_생성(host, "test2");
        Space space3 = Space_생성(host, "test3");
        spaceRepository.saveAll(List.of(space1, space2, space3));

        Slice<Space> result = spaceRepository.findByMember(host, PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getSize()).isEqualTo(2),
                () -> assertThat(result.hasNext()).isTrue()
        );
    }

    @Test
    void 멤버와_아이디로_공간을_조회한다() {
        Member host = memberRepository.save(Member_생성("1234"));
        Space space = Space_생성(host, "잠실");
        spaceRepository.save(space);

        Optional<Space> result = spaceRepository.findByMemberAndId(host, space.getId());

        assertThat(result).isNotEmpty();
    }

    @Test
    void 다른_호스트의_공간을_조회할_경우_빈_값이_조회된다() {
        Member host1 = memberRepository.save(Member_생성("1234"));
        Member host2 = memberRepository.save(Member_생성("1234"));
        Space space = Space_생성(host2, "잠실");
        spaceRepository.save(space);

        Optional<Space> result = spaceRepository.findByMemberAndId(host1, space.getId());

        assertThat(result).isEmpty();
    }
}
