package com.woowacourse.gongcheck.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.auth.application.response.TokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.TokenRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class HostAuthAcceptanceTest extends AcceptanceTest {
    
    @Test
    void 첫_로그인한_Host이면_회원가입하고_토큰을_발급한다() {
        TokenRequest tokenRequest = new TokenRequest("code");

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/fake/login")
                .then().log().all()
                .extract();

        TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(tokenResponse.getToken()).isNotNull(),
                () -> assertThat(tokenResponse.isAlreadyJoin()).isTrue()
        );
    }

    @Test
    void 첫_로그인한_Host가_아니면_토큰을_발급한다() {
        TokenRequest tokenRequest = new TokenRequest("code");

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/fake/signup")
                .then().log().all()
                .extract();

        TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(tokenResponse.getToken()).isNotNull(),
                () -> assertThat(tokenResponse.isAlreadyJoin()).isFalse()
        );
    }
}
