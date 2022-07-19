package com.woowacourse.gongcheck.presentation;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
public class AuthenticationContext {

    private String principal;
    private Authority authority;

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }

    public void setAuthority(final Authority authority) {
        this.authority = authority;
    }
}
