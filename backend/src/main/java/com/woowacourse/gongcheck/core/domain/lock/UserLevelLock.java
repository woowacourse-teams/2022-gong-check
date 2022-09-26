package com.woowacourse.gongcheck.core.domain.lock;

import java.util.function.Supplier;

public interface UserLevelLock {

    <T> T executeWithLock(final String lockName, final int timeOutSeconds, final Supplier<T> supplier);
}
