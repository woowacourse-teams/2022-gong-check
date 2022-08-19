package com.woowacourse.gongcheck.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class TaskAcceptanceTest extends AcceptanceTest {

    @Test
    void RunningTask를_생성한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RunningTask를_생성한다(token);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 이미_RunningTask가_존재하는_경우_생성에_실패한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);

        ExtractableResponse<Response> response = TaskAcceptanceTest.this.RunningTask를_생성한다(token);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void RunningTask에_대한_Sse_connection을_연결한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/runningTasks/connect")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void RunningTask를_생성하고_존재_여부를_확인한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/active")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void RunningTask의_존재여부를_확인한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/active")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 존재하지_않는_RunningTask를_조회하려는_경우_예외가_발생한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/runningTasks/connect")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void RunningTask의_체크상태를_변경한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/tasks/1/flip")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 존재하지_않는_RunningTask의_체크상태를_변경하려는_경우_예외가_발생한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/tasks/1/flip")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void Task를_조회한다() {
        String token = Host_토큰을_요청한다().getToken();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/tasks")
                .then().log().all()
                .extract();
        TasksResponse taskResponse = response.as(TasksResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(taskResponse.getSections()).hasSize(2)
        );
    }

    @Test
    void 해당_Section의_RunningTask를_모두_체크한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/sections/1/runningTask/allCheck")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 존재하지_않는_RunningTask를_모두_체크하려는_경우_예외가_발생한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/sections/1/runningTask/allCheck")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> RunningTask를_생성한다(final String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/runningTasks/new")
                .then().log().all()
                .extract();
    }
}
