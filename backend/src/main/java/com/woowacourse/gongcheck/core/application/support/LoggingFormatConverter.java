package com.woowacourse.gongcheck.core.application.support;

import java.util.List;
import java.util.stream.Collectors;

public class LoggingFormatConverter {

    private LoggingFormatConverter() {
    }

    public static String convertIdsToString(final List<Long> ids) {
        return ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
