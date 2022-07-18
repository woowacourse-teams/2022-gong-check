package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.JwtTokenProvider;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
import com.woowacourse.gongcheck.support.AuthorizationTokenExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    public AuthenticationInterceptor(final JwtTokenProvider jwtTokenProvider,
                                     final AuthenticationContext authenticationContext) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String token = AuthorizationTokenExtractor.extractToken(request)
                .orElseThrow(() -> new UnauthorizedException("헤더에 토큰 값이 정상적으로 존재하지 않습니다."));

        String subject = jwtTokenProvider.extractSubject(token);
        authenticationContext.setPrincipal(subject);
        Authority authority = jwtTokenProvider.extractAuthority(token);
        authenticationContext.setAuthority(authority);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
