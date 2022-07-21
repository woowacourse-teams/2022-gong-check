package com.woowacourse.gongcheck.presentation.aop;

import com.woowacourse.gongcheck.exception.UnauthorizedException;
import com.woowacourse.gongcheck.presentation.AuthenticationContext;
import com.woowacourse.gongcheck.presentation.Authority;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HostVerifier {

    private final AuthenticationContext authenticationContext;

    public HostVerifier(final AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    @Before("@annotation(com.woowacourse.gongcheck.presentation.aop.HostOnly)")
    public void checkHost() {
        Authority authority = authenticationContext.getAuthority();
        if (!authority.isHost()) {
            throw new UnauthorizedException("호스트만 입장 가능합니다.");
        }
    }
}
