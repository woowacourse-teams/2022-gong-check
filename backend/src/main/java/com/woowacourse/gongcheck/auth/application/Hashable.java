package com.woowacourse.gongcheck.auth.application;

public interface Hashable {

    String encode(final String input);

    String decode(final String input);
}
