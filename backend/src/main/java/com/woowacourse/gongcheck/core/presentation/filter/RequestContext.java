package com.woowacourse.gongcheck.core.presentation.filter;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
@RequestScope
@Getter
public class RequestContext {

    private ContentCachingRequestWrapper request;

    public void setRequest(final ContentCachingRequestWrapper request) {
        this.request = request;
    }
}
