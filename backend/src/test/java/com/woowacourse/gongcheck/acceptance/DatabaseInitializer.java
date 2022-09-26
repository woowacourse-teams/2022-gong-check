package com.woowacourse.gongcheck.acceptance;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseInitializer {

    private static final String TRUNCATE_QUERY = "TRUNCATE TABLE %s";
    private static final String ALTER_COLUMN_QUERY = "ALTER TABLE %s AUTO_INCREMENT = 1";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    private final List<String> tableNames = new ArrayList<>();

    @PostConstruct
    public void afterPropertiesSet() {
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                if (tableName.equals("sys_config")) {
                    continue;
                }
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            throw new RuntimeException("테이블 이름을 불러올 수 없습니다.");
        }
    }

    @Transactional
    public void truncateTables() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for (String tableName : tableNames) {
            truncateTable(tableName);
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    @Transactional
    public void initTable() {
        entityManager.createNativeQuery("INSERT INTO host (id, space_password, github_id, image_url, created_at)\n"
                + "VALUES (1, '1234', 2, 'test.com', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO space (host_id, name, created_at)\n"
                + "VALUES (1, '잠실', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO job (space_id, name, slack_url, created_at)\n"
                + "VALUES (1, '청소', 'http://slackurl.com', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO section (job_id, name, created_at, image_url, description)\n"
                + "VALUES (1, '트랙룸', current_timestamp(), 'image_url', '설명')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO task (section_id, name, created_at, image_url, description)\n"
                + "VALUES (1, '책상 청소', current_timestamp(), 'image_url', '설명')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO task (section_id, name, created_at, image_url, description)\n"
                + "VALUES (1, '빈백 털기', current_timestamp(), 'image_url', '설명')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO section (job_id, name, created_at, image_url, description)\n"
                + "VALUES (1, '굿샷 강의장', current_timestamp(), 'image_url', '설명')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO task (section_id, name, created_at, image_url, description)\n"
                + "VALUES (2, '책상 청소', current_timestamp(), 'image_url', '설명')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO task (section_id, name, created_at, image_url, description)\n"
                + "VALUES (2, '의자 청소', current_timestamp(), 'image_url', '설명')").executeUpdate();
    }

    private void truncateTable(final String tableName) {
        entityManager.createNativeQuery(String.format(TRUNCATE_QUERY, tableName)).executeUpdate();
        if (tableName.equals("RUNNING_TASK")) {
            return;
        }
        entityManager.createNativeQuery(String.format(ALTER_COLUMN_QUERY, tableName)).executeUpdate();
    }
}
