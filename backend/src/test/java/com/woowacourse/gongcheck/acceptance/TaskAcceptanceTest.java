package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class TaskAcceptanceTest extends AcceptanceTest {

    @Test
    void 새로운_진행작업을_생성한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 이미_존재하는_진행작업이_있는데_생성하는_경우_실패한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all()
                .extract();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 진행중인_작업을_조회한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all()
                .extract();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/tasks")
                .then().log().all()
                .extract();
        RunningTasksResponse runningTasksResponse = response.as(RunningTasksResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(runningTasksResponse.getSections()).hasSize(2)
        );
    }

    @Test
    void 진행_작업을_생성하고_작업의_진행여부를_확인한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all()
                .extract();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/active")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 진행_작업이_없는_경우에_작업의_진행여부를_확인한다() {
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
    void 진행중인_작업이_존재하지_않는데_조회할_경우_예외가_발생한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/tasks")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 진행중인_작업의_체크_상태를_변환한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all()
                .extract();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/tasks/1/flip")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 진행중이지_않은_작업을_체크시도할_경우_예외가_발생한다() {
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
}
