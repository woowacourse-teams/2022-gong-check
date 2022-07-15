package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.exception.UnauthorizedException;
import com.woowacourse.gongcheck.presentation.Authority;

public interface JwtTokenProvider {
    String createToken(final String subject, final Authority authority);

    String extractSubject(final String token) throws UnauthorizedException;

    Authority extractAuthority(final String token);
}
