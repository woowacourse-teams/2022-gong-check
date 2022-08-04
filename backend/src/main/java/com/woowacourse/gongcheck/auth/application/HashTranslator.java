package com.woowacourse.gongcheck.auth.application;

public interface HashTranslator {

    String encode(final String input);

    String decode(final String input);
}
