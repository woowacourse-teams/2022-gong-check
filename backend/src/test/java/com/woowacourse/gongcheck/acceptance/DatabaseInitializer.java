package com.woowacourse.gongcheck.acceptance;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseInitializer implements InitializingBean {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    private List<String> tableNames = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void truncateTables() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for (String tableName : tableNames) {
            truncateTable(tableName);
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    @Transactional
    public void initTable() {
        entityManager.createNativeQuery("INSERT INTO host (space_password, github_id, image_url, created_at)\n"
                + "VALUES ('1234', 2, 'test.com', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO space (host_id, name, created_at)\n"
                + "VALUES (1, '잠실', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO job (space_id, name, slack_url, created_at)\n"
                + "VALUES (1, '청소', 'http://slackurl.com', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO section (job_id, name, created_at)\n"
                + "VALUES (1, '트랙룸', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO task (section_id, name, created_at)\n"
                + "VALUES (1, '책상 청소', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO task (section_id, name, created_at)\n"
                + "VALUES (1, '빈백 털기', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO section (job_id, name, created_at)\n"
                + "VALUES (1, '굿샷 강의장', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO task (section_id, name, created_at)\n"
                + "VALUES (2, '책상 청소', current_timestamp())").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO task (section_id, name, created_at)\n"
                + "VALUES (2, '의자 청소', current_timestamp())").executeUpdate();
    }

    private void truncateTable(final String tableName) {
        entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        if (tableName.equals("RUNNING_TASK")) {
            return;
        }
        entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN "
                + "id RESTART WITH 1").executeUpdate();
    }
}

