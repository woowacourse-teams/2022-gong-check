package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.presentation.request.SpaceCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SpaceAcceptanceTest extends AcceptanceTest {

    @Test
    void 공간을_조회한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces?page=0&size=5")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 공간을_생성한다() {
        SpaceCreateRequest spaceCreateRequest = new SpaceCreateRequest("잠실 캠퍼스", "https://image.com");

        // 호스트 로그인 구현 전까지 토큰 입력용으로 사용
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(spaceCreateRequest)
                .auth().oauth2(token)
                .when().post("/api/spaces")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
