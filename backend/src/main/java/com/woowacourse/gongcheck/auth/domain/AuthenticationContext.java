package com.woowacourse.gongcheck.auth.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
public class AuthenticationContext {

    private String principal;

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }
}
