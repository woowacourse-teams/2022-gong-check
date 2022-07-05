package com.woowacourse.gongcheck.support;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;

public class AuthorizationTokenExtractor {

    private static final Pattern JWT_TOKEN_PATTERN = Pattern.compile(
            "^Bearer \\b([A-Za-z\\d-_]*\\.[A-Za-z\\d-_]*\\.[A-Za-z\\d-_]*)$");

    private AuthorizationTokenExtractor() {
    }

    public static Optional<String> extractToken(final HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isEmpty(header)) {
            return Optional.empty();
        }

        Matcher matcher = JWT_TOKEN_PATTERN.matcher(header);
        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }
}
