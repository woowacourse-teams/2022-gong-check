package com.woowacourse.gongcheck;

import com.woowacourse.gongcheck.acceptance.DatabaseInitializer;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseCleanerExtension implements AfterEachCallback {

    @Override
    public void afterEach(final ExtensionContext context) {
        executeDatabaseInitialize(context);
    }

    private void executeDatabaseInitialize(final ExtensionContext context) {
        DatabaseInitializer databaseInitializer = (DatabaseInitializer) SpringExtension
                .getApplicationContext(context).getBean("databaseInitializer");
        databaseInitializer.truncateTables();
    }
}
