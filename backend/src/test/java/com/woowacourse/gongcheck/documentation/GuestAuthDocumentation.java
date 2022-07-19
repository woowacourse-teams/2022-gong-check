package com.woowacourse.gongcheck.documentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorResponse;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class GuestAuthDocumentation extends DocumentationTest {

    @Nested
    class 게스트_토큰을_요청한다 {

        @Test
        void 올바른_비밀번호를_입력하면_게스트_토큰을_반환한다() {
            when(guestAuthService.createToken(anyLong(), any())).thenReturn(GuestTokenResponse.from("jwt.token.here"));
            GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");

            docsGiven
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(guestEnterRequest)
                    .when().post("/api/hosts/1/enter")
                    .then().log().all()
                    .apply(document("guests/auth/success"))
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 비밀번호_길이가_맞지_않을_경우_예외가_발생한다() {
            GuestEnterRequest guestEnterRequest = new GuestEnterRequest("12345");
            doThrow(new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다."))
                    .when(guestAuthService).createToken(anyLong(), any());

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(guestEnterRequest)
                    .when().post("/api/hosts/1/enter")
                    .then().log().all()
                    .apply(document("guests/auth/fail/length"))
                    .extract();

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.jsonPath().getObject(".", ErrorResponse.class).getMessage())
                            .isEqualTo("비밀번호는 네 자리 숫자로 이루어져야 합니다.")
            );
        }

        @Test
        void 비밀번호가_숫자가_아닐_경우_예외가_발생한다() {
            GuestEnterRequest guestEnterRequest = new GuestEnterRequest("abcd");
            doThrow(new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다."))
                    .when(guestAuthService).createToken(anyLong(), any());

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(guestEnterRequest)
                    .when().post("/api/hosts/1/enter")
                    .then().log().all()
                    .apply(document("guests/auth/fail/pattern"))
                    .extract();

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.jsonPath().getObject(".", ErrorResponse.class).getMessage())
                            .isEqualTo("비밀번호는 네 자리 숫자로 이루어져야 합니다.")
            );
        }
    }
}
