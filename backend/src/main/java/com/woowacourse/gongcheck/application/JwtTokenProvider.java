package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.exception.UnauthorizedException;

public interface JwtTokenProvider {
    String createToken(final String subject);

    String extractSubject(final String token) throws UnauthorizedException;
}
