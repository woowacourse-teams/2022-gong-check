package com.woowacourse.gongcheck.config;

import com.woowacourse.gongcheck.core.domain.lock.JdbcUserLevelLock;
import com.woowacourse.gongcheck.core.domain.lock.UserLevelLock;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class DatabaseConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("submission-lock.hikari")
    public HikariDataSource submissionLockDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public UserLevelLock userLevelLock() {
        return new JdbcUserLevelLock(submissionLockDataSource());
    }
}
