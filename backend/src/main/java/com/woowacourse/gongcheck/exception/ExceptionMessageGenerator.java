package com.woowacourse.gongcheck.exception;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class ExceptionMessageGenerator {

    private static final String MESSAGE_FORMAT = "Request Information\n%s %s\n%s\nParams: %s\nBody : %s\nException Message : %s";
    private static final String GENERATE_MESSAGE_EXCEPTION = "예외 메세지 생성 중 예외가 발생했습니다.";

    private ExceptionMessageGenerator() {
    }

    public static String generate(final ContentCachingRequestWrapper request, final Exception exception) {
        try {
            String method = request.getMethod();
            String requestURI = request.getRequestURI();
            String headers = getHeaders(request);
            String params = getParams(request);
            String body = getBody(request);

            return String.format(MESSAGE_FORMAT, method, requestURI, headers, params, body, exception.getMessage());
        } catch (Exception e) {
            return GENERATE_MESSAGE_EXCEPTION;
        }
    }

    private static String getHeaders(final ContentCachingRequestWrapper request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        return extractRequestEnumerationInfo(request, headerNames);
    }

    private static String getParams(final ContentCachingRequestWrapper request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        return extractRequestEnumerationInfo(request, parameterNames);
    }

    private static String extractRequestEnumerationInfo(final ContentCachingRequestWrapper request,
                                                        final Enumeration<String> parameterNames) {
        Map<String, String> parameters = new HashMap<>();

        while (parameterNames.hasMoreElements()) {
            String headerName = parameterNames.nextElement();
            parameters.put(headerName, request.getHeader(headerName));
        }

        return parameters.entrySet().stream()
                .map(i -> i.getKey() + ":" + i.getValue())
                .collect(Collectors.joining("\n"));
    }

    private static String getBody(final ContentCachingRequestWrapper request) {
        return new String(request.getContentAsByteArray());
    }
}
