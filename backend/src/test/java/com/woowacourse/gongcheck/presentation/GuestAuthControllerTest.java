package com.woowacourse.gongcheck.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.exception.ErrorResponse;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class GuestAuthControllerTest extends ControllerTest {

    @Test
    void 비밀번호_길이가_맞지_않을_경우_예외가_발생한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("12345");

        ExtractableResponse<MockMvcResponse> response = given
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(guestEnterRequest)
                .when().post("/api/hosts/1/enter")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getObject(".", ErrorResponse.class).getMessage())
                        .isEqualTo("비밀번호 길이가 올바르지 않습니다")
        );
    }

    @Test
    void 비밀번호가_숫자가_아닐_경우_예외가_발생한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("abcd");

        ExtractableResponse<MockMvcResponse> response = given
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(guestEnterRequest)
                .when().post("/api/hosts/1/enter")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getObject(".", ErrorResponse.class).getMessage())
                        .isEqualTo("비밀번호는 숫자로만 이루어져 있어야 합니다")
        );
    }
}
