package com.woowacourse.gongcheck.core.domain.exception;

import lombok.Getter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Getter
public class RequestContext {

    private ContentCachingRequestWrapper request;

    public void setRequest(final ContentCachingRequestWrapper request) {
        this.request = request;
    }

    public ContentCachingRequestWrapper getRequest() {
        return request;
    }
}
