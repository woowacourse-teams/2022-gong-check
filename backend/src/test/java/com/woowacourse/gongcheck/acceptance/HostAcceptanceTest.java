package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.acceptance.AuthSupport.Host_토큰을_요청한다;
import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.core.presentation.request.SpacePasswordChangeRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class HostAcceptanceTest extends AcceptanceTest {

    @Test
    void Host_토큰으로_Space_비밀번호를_변경한다() {
        String token = Host_토큰을_요청한다().getToken();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(new SpacePasswordChangeRequest("4567"))
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/api/spacePassword")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Guest_토큰으로_Space_비밀번호를_변경_요청_시_예외가_발생한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(new SpacePasswordChangeRequest("4567"))
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/api/spacePassword")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void Host_토큰으로_호스트_아이디를_조회한다() {
        String token = Host_토큰을_요청한다().getToken();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/hosts/me")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
