package com.woowacourse.gongcheck.core.application.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class LoggingFormatConverterTest {

    @Test
    void Long타입_리스트를_입력받아_문자열로_반환한다() {
        List<Long> ids = List.of(1L, 2L, 3L, 4L);
        String result = LoggingFormatConverter.convertIdsToString(ids);

        List<String> expected = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        assertThat(result).contains(expected);
    }
}
