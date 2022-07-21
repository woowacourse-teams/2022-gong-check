package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SubmissionAcceptanceTest extends AcceptanceTest {

    @Test
    void RunningTask가_모두_체크된_상테로_Submission을_생성한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);
        체크상태를_변경한다(token, 1L);
        체크상태를_변경한다(token, 2L);
        체크상태를_변경한다(token, 3L);
        체크상태를_변경한다(token, 4L);
        SubmissionRequest submissionRequest = new SubmissionRequest("제출자");

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(submissionRequest)
                .when().post("/api/jobs/1/complete")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void RunningTask가_모두_체크되지_않은_상태로_Submission을_생성할_수_없다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);
        체크상태를_변경한다(token, 1L);
        SubmissionRequest submissionRequest = new SubmissionRequest("제출자");

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(submissionRequest)
                .when().post("/api/jobs/1/complete")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void RunningTask를_생성한다(final String token) {
        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all();
    }

    private void 체크상태를_변경한다(final String token, final Long taskId) {
        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/tasks/" + taskId + "/flip")
                .then().log().all();
    }
}
