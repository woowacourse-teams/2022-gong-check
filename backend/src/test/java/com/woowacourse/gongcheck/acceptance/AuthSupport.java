package com.woowacourse.gongcheck.acceptance;

import com.woowacourse.gongcheck.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.application.response.TokenResponse;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.presentation.request.TokenRequest;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class AuthSupport {

    public static String 토큰을_요청한다(final GuestEnterRequest guestEnterRequest) {
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

    public static TokenResponse Host_토큰을_요청한다(final TokenRequest tokenRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/fake/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class);
    }
}
