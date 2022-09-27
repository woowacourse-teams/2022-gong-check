package com.woowacourse.gongcheck.core.domain.lock;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.ApplicationTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
class JdbcUserLevelLockTest {

    @Autowired
    private JdbcUserLevelLock jdbcUserLevelLock;

    @Nested
    class executeWithLock_메소드는 {

        @Nested
        class 두_스레드가_동시에_요청하는_경우 {

            private final String lockName = "lock";
            private final int timeOutSeconds = 2;
            private int count = 0;
            private final int threadCount = 10;
            private final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

            @Test
            void 순차적으로_실행시킨다() throws InterruptedException {
                runThreadPool(() -> jdbcUserLevelLock.executeWithLock(lockName, timeOutSeconds, () -> convertCount()));

                assertThat(count).isEqualTo(10);
            }

            private void runThreadPool(Runnable runnable) throws InterruptedException {
                CountDownLatch latch = new CountDownLatch(threadCount);
                for (int i = 0; i < threadCount; i++) {
                    executorService.submit(() -> {
                        try {
                            runnable.run();
                        } finally {
                            latch.countDown();
                        }
                    });
                }
                latch.await();
            }

            private void convertCount() {
                if (count == 0) {
                    count = 1;
                    return;
                }
                if (count == 1) {
                    count = 2;
                    return;
                }
                if (count == 2) {
                    count = 3;
                    return;
                }
                if (count == 3) {
                    count = 4;
                    return;
                }
                if (count == 4) {
                    count = 5;
                    return;
                }
                if (count == 5) {
                    count = 6;
                    return;
                }
                if (count == 6) {
                    count = 7;
                    return;
                }
                if (count == 7) {
                    count = 8;
                    return;
                }
                if (count == 8) {
                    count = 9;
                    return;
                }
                if (count == 9) {
                    count = 10;
                    return;
                }
                if (count == 10) {
                    count = 11;
                    return;
                }
                count = 3;
            }
        }
    }
}
