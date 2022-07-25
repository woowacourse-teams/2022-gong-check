package com.woowacourse.gongcheck.auth.application;

import com.woowacourse.gongcheck.auth.domain.Authority;
import com.woowacourse.gongcheck.exception.UnauthorizedException;

public interface JwtTokenProvider {
    String createToken(final String subject, final Authority authority);

    String extractSubject(final String token) throws UnauthorizedException;

    Authority extractAuthority(final String token) throws UnauthorizedException;
}
