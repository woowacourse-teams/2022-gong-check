package com.woowacourse.gongcheck.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.auth.application.EntranceCodeProvider;
import com.woowacourse.gongcheck.auth.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class GuestAuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private EntranceCodeProvider entranceCodeProvider;

    @Test
    void 올바른_Space_비밀번호를_입력하면_토큰을_반환한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String entranceCode = entranceCodeProvider.createEntranceCode(1L);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(guestEnterRequest)
                .when().post("/api/hosts/{entranceCode}/enter/", entranceCode)
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getObject(".", GuestTokenResponse.class)).isNotNull()
        );
    }
}
