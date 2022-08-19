package com.woowacourse.gongcheck.documentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.woowacourse.gongcheck.auth.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import com.woowacourse.gongcheck.exception.ErrorResponse;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class GuestAuthDocumentation extends DocumentationTest {

    private static final String ENTRANCE_CODE = "random_entrance_code";

    @Nested
    class 게스트_토큰을_요청한다 {

        @Test
        void 올바른_비밀번호를_입력하면_게스트_토큰을_반환한다() {
            when(guestAuthService.createToken(anyLong(), any())).thenReturn(GuestTokenResponse.from("jwt.token.here"));
            GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");

            docsGiven
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(guestEnterRequest)
                    .when().post("/api/hosts/{entranceCode}/enter", ENTRANCE_CODE)
                    .then().log().all()
                    .apply(document("guests/auth/success",
                            pathParameters(
                                    parameterWithName("entranceCode").description("호스트가 제공하는 입장코드")),
                            requestFields(
                                    fieldWithPath("password").type(JsonFieldType.STRING).description("공간 비밀번호")
                                            .attributes(key("length").value(4))
                                            .attributes(key("nullable").value(true))),
                            responseFields(
                                    fieldWithPath("token").type(JsonFieldType.STRING)
                                            .description("Access Token")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 비밀번호_길이가_맞지_않을_경우_예외가_발생한다() {
            GuestEnterRequest guestEnterRequest = new GuestEnterRequest("12345");
            doThrow(new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다.", ErrorCode.SP03))
                    .when(guestAuthService).createToken(anyLong(), any());

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(guestEnterRequest)
                    .when().post("/api/hosts/{entranceCode}/enter", ENTRANCE_CODE)
                    .then().log().all()
                    .apply(document("guests/auth/fail/length"))
                    .extract();

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.jsonPath().getObject(".", ErrorResponse.class).getErrorCode())
                            .isEqualTo(ErrorCode.SP03.name())
            );
        }

        @Test
        void 비밀번호가_숫자가_아닐_경우_예외가_발생한다() {
            GuestEnterRequest guestEnterRequest = new GuestEnterRequest("abcd");
            doThrow(new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다.", ErrorCode.SP03))
                    .when(guestAuthService).createToken(anyLong(), any());

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(guestEnterRequest)
                    .when().post("/api/hosts/{entranceCode}/enter", ENTRANCE_CODE)
                    .then().log().all()
                    .apply(document("guests/auth/fail/pattern"))
                    .extract();

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.jsonPath().getObject(".", ErrorResponse.class).getErrorCode())
                            .isEqualTo(ErrorCode.SP03.name())
            );
        }
    }
}
