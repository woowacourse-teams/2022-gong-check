package com.woowacourse.gongcheck.acceptance;

import static io.restassured.RestAssured.UNDEFINED_PORT;

import com.woowacourse.gongcheck.core.application.AlertService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class AcceptanceTest {

    @MockBean
    private AlertService alertService;

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setPort() {
        if (RestAssured.port == UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseInitializer.afterPropertiesSet();
        }
        databaseInitializer.initTable();
    }

    @AfterEach
    void clean() {
        databaseInitializer.truncateTables();
    }
}
