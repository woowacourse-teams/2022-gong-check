package com.woowacourse.gongcheck.core.domain.lock;

public interface UserLevelLock {

    void executeWithLock(final String lockName, final int timeOutSeconds, final Runnable runnable);
}
