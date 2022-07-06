package com.woowacourse.gongcheck.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorResponse;
import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SubmissionControllerTest extends ControllerTest {

    @Test
    void 제출자_이름의_길이가_올바르지_않을_경우_예외가_발생한다() {
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        SubmissionRequest submissionRequest = new SubmissionRequest("123456789123456789123");
        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(submissionRequest)
                .when().post("/api/jobs/1/complete")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage())
                        .isEqualTo("제출자 이름의 길이가 올바르지 않습니다.")
        );
    }

    @Test
    void 제출자_이름이_null_일_경우_예외가_발생한다() {
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        SubmissionRequest submissionRequest = new SubmissionRequest(null);
        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(submissionRequest)
                .when().post("/api/jobs/1/complete")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage())
                        .isEqualTo("제출자 이름은 null 일 수 없습니다.")
        );
    }

    @Test
    void 현재_진행중인_작업이_없는데_제출을_시도할_경우_예외가_발생한다() {
        doThrow(new BusinessException("현재 제출할 수 있는 진행중인 작업이 존재하지 않습니다."))
                .when(submissionService)
                .submitJobCompletion(anyLong(), anyLong(), any());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        SubmissionRequest submissionRequest = new SubmissionRequest("제출자");
        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(submissionRequest)
                .when().post("/api/jobs/1/complete")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage())
                        .isEqualTo("현재 제출할 수 있는 진행중인 작업이 존재하지 않습니다.")
        );
    }

    @Test
    void 현재_진행중인_작업을_미완료_상태로_제출을_시도할_경우_예외가_발생한다() {
        doThrow(new BusinessException("모든 작업이 완료되지않아 제출이 불가합니다."))
                .when(submissionService)
                .submitJobCompletion(anyLong(), anyLong(), any());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        SubmissionRequest submissionRequest = new SubmissionRequest("제출자");
        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(submissionRequest)
                .when().post("/api/jobs/1/complete")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.as(ErrorResponse.class).getMessage())
                        .isEqualTo("모든 작업이 완료되지않아 제출이 불가합니다.")
        );
    }
}
