package com.woowacourse.gongcheck.infrastructure.lock;

import com.woowacourse.gongcheck.core.domain.lock.UserLevelLock;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


public class MySQLUserLevelLock implements UserLevelLock {

    private static final String GET_LOCK_QUERY = "select get_lock(?, ?)";
    private static final String RELEASE_LOCK_QUERY = "select release_lock(?)";

    private final JdbcTemplate jdbcTemplate;

    public MySQLUserLevelLock(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional(transactionManager = "submissionLockTransactionManager")
    public void executeWithLock(final String lockName, final int timeOutSeconds, final Runnable runnable) {
        try {
            getLock(lockName, timeOutSeconds);
            runnable.run();
        } finally {
            releaseLock(lockName);
        }
    }

    private void getLock(final String lockName, final int timeOutSeconds) {
        jdbcTemplate.queryForObject(GET_LOCK_QUERY, Integer.class, lockName, timeOutSeconds);
    }

    private void releaseLock(final String lockName) {
        jdbcTemplate.queryForObject(RELEASE_LOCK_QUERY, Integer.class, lockName);
    }
}
