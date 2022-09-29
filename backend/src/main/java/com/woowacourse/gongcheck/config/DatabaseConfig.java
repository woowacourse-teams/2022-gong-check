package com.woowacourse.gongcheck.config;

import com.woowacourse.gongcheck.core.domain.lock.UserLevelLock;
import com.woowacourse.gongcheck.infrastructure.lock.MySQLUserLevelLock;
import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

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

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean
    public PlatformTransactionManager submissionLockTransactionManager() {
        return new DataSourceTransactionManager(submissionLockDataSource());
    }

    @Bean
    public UserLevelLock userLevelLock() {
        return new MySQLUserLevelLock(submissionLockDataSource());
    }
}
