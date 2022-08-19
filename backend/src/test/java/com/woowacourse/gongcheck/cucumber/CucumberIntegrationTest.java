package com.woowacourse.gongcheck.cucumber;

import com.woowacourse.gongcheck.acceptance.DatabaseInitializer;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CucumberIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @Before("@api")
    public void setupForApi() {
        RestAssured.port = port;
        databaseInitializer.initTable();
    }
}
