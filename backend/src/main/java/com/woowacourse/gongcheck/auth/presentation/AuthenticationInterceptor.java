package com.woowacourse.gongcheck.auth.presentation;

import com.woowacourse.gongcheck.auth.application.JwtTokenProvider;
import com.woowacourse.gongcheck.auth.domain.AuthenticationContext;
import com.woowacourse.gongcheck.auth.domain.Authority;
import com.woowacourse.gongcheck.auth.support.JwtTokenExtractor;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
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

        String token = JwtTokenExtractor.extractToken(request)
                .orElseThrow(() -> new UnauthorizedException("헤더에 토큰 값이 정상적으로 존재하지 않습니다."));

        String subject = jwtTokenProvider.extractSubject(token);
        authenticationContext.setPrincipal(subject);

        if (isNotAnnotated((HandlerMethod) handler)) {
            return true;
        }
        Authority authority = jwtTokenProvider.extractAuthority(token);
        authorize(authority);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean isNotAnnotated(final HandlerMethod handlerMethod) {
        return Objects.isNull(handlerMethod.getMethodAnnotation(HostOnly.class));
    }

    private void authorize(final Authority authority) {
        if (!authority.isHost()) {
            throw new UnauthorizedException("호스트만 입장 가능합니다.");
        }
    }
}
