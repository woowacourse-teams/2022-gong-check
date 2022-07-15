package com.woowacourse.gongcheck.documentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.SubmissionResponse;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorResponse;
import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SubmissionDocumentation extends DocumentationTest {

    @Nested
    class 작업을_제출한다 {

        @Test
        void 현재_진행중인_작업이_모두_완료된_상태로_제출하면_제출에_성공한다() {
            SubmissionResponse response = SubmissionResponse.of("author",
                    Job.builder().space(Space.builder().build()).build());
            when(submissionService.submitJobCompletion(anyLong(), anyLong(), any())).thenReturn(response);
            doNothing().when(alertService).sendMessage(response);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            SubmissionRequest submissionRequest = new SubmissionRequest("제출자");
            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(submissionRequest)
                    .when().post("/api/jobs/1/complete")
                    .then().log().all()
                    .apply(document("submissions/submit/success"))
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 제출자_이름의_길이가_올바르지_않을_경우_예외가_발생한다() {
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            SubmissionRequest submissionRequest = new SubmissionRequest("123456789123456789123");
            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(submissionRequest)
                    .when().post("/api/jobs/1/complete")
                    .then().log().all()
                    .apply(document("submissions/submit/fail/length"))
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
            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(submissionRequest)
                    .when().post("/api/jobs/1/complete")
                    .then().log().all()
                    .apply(document("submissions/submit/fail/null"))
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
            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(submissionRequest)
                    .when().post("/api/jobs/1/complete")
                    .then().log().all()
                    .apply(document("submissions/submit/fail/active"))
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
            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(submissionRequest)
                    .when().post("/api/jobs/1/complete")
                    .then().log().all()
                    .apply(document("submissions/submit/fail/complete"))
                    .extract();

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.as(ErrorResponse.class).getMessage())
                            .isEqualTo("모든 작업이 완료되지않아 제출이 불가합니다.")
            );
        }
    }
}
