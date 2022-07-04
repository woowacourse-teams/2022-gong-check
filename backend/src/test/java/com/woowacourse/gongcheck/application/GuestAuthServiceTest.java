package com.woowacourse.gongcheck.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.domain.member.Member;
import com.woowacourse.gongcheck.domain.member.MemberRepository;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GuestAuthServiceTest {

    @Autowired
    private GuestAuthService guestAuthService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 토큰_발급_시_해당하는_호스트가_존재하지_않으면_예외가_발생한다() {
        assertThatThrownBy(() -> guestAuthService.createToken(0L, new GuestEnterRequest("1234")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }

    @Test
    void 토큰_발급_시_비밀번호가_틀리면_예외가_발생한다() {
        Member member = memberRepository.save(Member.builder()
                .spacePassword("0123")
                .createdAt(LocalDateTime.now())
                .build());

        assertThatThrownBy(() -> guestAuthService.createToken(member.getId(), new GuestEnterRequest("1234")))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("공간 비밀번호와 입력하신 비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 토큰_발급_시_정상적으로_토큰을_발행한다() {
        Member member = memberRepository.save(Member.builder()
                .spacePassword("0123")
                .createdAt(LocalDateTime.now())
                .build());
        GuestTokenResponse token = guestAuthService.createToken(member.getId(), new GuestEnterRequest("0123"));

        assertThat(token.getToken()).isNotNull();
    }
}
