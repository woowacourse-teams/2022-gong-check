package com.woowacourse.gongcheck.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class JobAcceptanceTest extends AcceptanceTest {

    @Test
    void 작업을_조회한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces/1/jobs?page=0&size=5")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private String 토큰을_요청한다(final GuestEnterRequest guestEnterRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(guestEnterRequest)
                .when().post("/api/hosts/1/enter")
                .then().log().all()
                .extract()
                .as(GuestTokenResponse.class)
                .getToken();
    }
}
