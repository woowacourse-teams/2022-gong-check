package com.woowacourse.gongcheck.core.application.support;

import java.util.List;
import java.util.stream.Collectors;

public class LoggingFormatConverter {

    private static final String ID_DELIMITER = ", ";

    private LoggingFormatConverter() {
    }

    public static String convertIdsToString(final List<Long> ids) {
        return ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(ID_DELIMITER));
    }
}
