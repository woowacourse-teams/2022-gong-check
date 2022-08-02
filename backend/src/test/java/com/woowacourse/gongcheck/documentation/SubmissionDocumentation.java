package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Submission_아이디_지정_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.woowacourse.gongcheck.core.application.response.SubmissionCreatedResponse;
import com.woowacourse.gongcheck.core.application.response.SubmissionsResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.submission.Submission;
import com.woowacourse.gongcheck.core.presentation.request.SubmissionRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorResponse;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class SubmissionDocumentation extends DocumentationTest {

    @Nested
    class Submission을_생성한다 {

        @Test
        void RunningTaks가_모두_체크상태이면_성공한다() {
            Host host = Host_생성("1234", 1234L);
            Space space = Space_아이디_지정_생성(1L, host, "잠실");
            Job job = Job_아이디_지정_생성(1L, space, "청소");
            SubmissionCreatedResponse response = SubmissionCreatedResponse.of("author", job);
            when(submissionService.submitJobCompletion(anyLong(), anyLong(), any())).thenReturn(response);
            doNothing().when(alertService).sendMessage(response);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            SubmissionRequest submissionRequest = new SubmissionRequest("제출자");
            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(submissionRequest)
                    .when().post("/api/jobs/{jobId}/complete", 1)
                    .then().log().all()
                    .apply(document("submissions/submit/success",
                            pathParameters(
                                    parameterWithName("jobId").description("Submission을 제출할 Job Id")),
                            requestFields(
                                    fieldWithPath("author").type(JsonFieldType.STRING)
                                            .description("제출자")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void author_길이가_올바르지_않은_경우_예외가_발생한다() {
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
        void author가_null_일_경우_예외가_발생한다() {
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
        void RunningTask가_존재하지_않으면_예외가_발생한다() {
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
        void 모든_RunningTask가_체크상태가_아니면_예외가_발생한다() {
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

    @Nested
    class Submission_목록_조회 {

        @Test
        void Submission_목록_조회에_성공한다() {
            Host host = Host_생성("1234", 1234L);
            Space space = Space_아이디_지정_생성(1L, host, "잠실");
            Job job1 = Job_아이디_지정_생성(1L, space, "청소");
            Job job2 = Job_아이디_지정_생성(2L, space, "마감");
            Submission submission1 = Submission_아이디_지정_생성(1L, job1);
            Submission submission2 = Submission_아이디_지정_생성(2L, job2);
            SubmissionsResponse response = SubmissionsResponse.of(List.of(submission1, submission2), true);

            when(submissionService.findPage(anyLong(), anyLong(), any())).thenReturn(response);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .queryParam("page", 0)
                    .queryParam("size", 2)
                    .when().get("/api/spaces/{spaceId}/submissions", 1)
                    .then().log().all()
                    .apply(document("submissions/list",
                            pathParameters(
                                    parameterWithName("spaceId").description("Submission 목록을 조회할 Space Id")),
                            responseFields(
                                    fieldWithPath("submissions.[].submissionId").type(JsonFieldType.NUMBER)
                                            .description("Submission Id"),
                                    fieldWithPath("submissions.[].jobId").type(JsonFieldType.NUMBER)
                                            .description("Job Id"),
                                    fieldWithPath("submissions.[].jobName").type(JsonFieldType.STRING)
                                            .description("Job 이름"),
                                    fieldWithPath("submissions.[].author").type(JsonFieldType.STRING)
                                            .description("제출자"),
                                    fieldWithPath("submissions.[].createdAt").type(JsonFieldType.STRING)
                                            .description("제출 날짜"),
                                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN)
                                            .description("다음 페이지 존재 여부")
                            )))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
