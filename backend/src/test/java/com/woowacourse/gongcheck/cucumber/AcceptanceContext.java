package com.woowacourse.gongcheck.cucumber;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "cucumber-glue")
public class AcceptanceContext {

    public RequestSpecification request;
    public Response response;
    public Map<String, Object> storage;

    public AcceptanceContext() {
        reset();
    }

    private void reset() {
        request = null;
        response = null;
        storage = new HashMap<>();
    }

    public void invokeHttpPost(String path, Object data) {
        request = RestAssured
                .given().log().all()
                .body(data).contentType(ContentType.JSON);
        response = request.post(path);
        response.then().log().all();
    }
}

