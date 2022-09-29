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
class MySQLUserLevelLockTest {

    @Autowired
    private UserLevelLock mySQLUserLevelLock;

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
                runThreadPool(() -> mySQLUserLevelLock.executeWithLock(lockName, timeOutSeconds, this::convertCount));

                assertThat(count).isEqualTo(threadCount);
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
                this.count++;
            }
        }
    }
}
