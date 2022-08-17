package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.core.domain.exception.RequestContext;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class ServletWrappingFilter extends OncePerRequestFilter {

    private final RequestContext requestContext;

    public ServletWrappingFilter(final RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        requestContext.setRequest(requestWrapper);

        filterChain.doFilter(requestWrapper, response);
    }
}
