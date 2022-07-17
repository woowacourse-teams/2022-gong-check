package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;

import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.presentation.request.SpacePasswordChangeRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class HostAcceptanceTest extends AcceptanceTest {

    @Test
    void 공간_비밀번호를_변경한다() {
        // 호스트 로그인 구현 전까지 토큰 입력용으로 사용
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(new SpacePasswordChangeRequest("1234"))
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/api/spacePassword")
                .then().log().all()
                .extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
