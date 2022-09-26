package com.woowacourse.gongcheck;


import com.woowacourse.gongcheck.core.domain.lock.JdbcUserLevelLock;
import com.woowacourse.gongcheck.core.domain.lock.UserLevelLock;
import com.zaxxer.hikari.HikariDataSource;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestcontainerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TestcontainerConfiguration.class);

    public final static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.30"))
            .withDatabaseName("gongcheck-test")
            .withUsername("root")
            .withPassword("password")
            .withInitScript("schema.sql")
            .withReuse(true);

    static {
        mySQLContainer.start();

        log.info("🐳 TestContainers started at {}", Instant.now());
        log.info("🐳 TestContainers port {}", mySQLContainer.getJdbcUrl());
    }

    @Bean
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(mySQLContainer.getDriverClassName())
                .username(mySQLContainer.getUsername())
                .password(mySQLContainer.getPassword())
                .url(mySQLContainer.getJdbcUrl())
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public UserLevelLock userLevelLock() {
        return new JdbcUserLevelLock(DataSourceBuilder.create()
                .driverClassName(mySQLContainer.getDriverClassName())
                .username(mySQLContainer.getUsername())
                .password(mySQLContainer.getPassword())
                .url(mySQLContainer.getJdbcUrl())
                .type(HikariDataSource.class)
                .build());
    }
}
