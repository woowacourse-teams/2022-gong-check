package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.exception.UnauthorizedException;

public interface JwtTokenProvider {
    String createToken(String subject);

    String extractSubject(String token) throws UnauthorizedException;
}
