package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.presentation.request.TaskCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class JobAcceptanceTest extends AcceptanceTest {

    @Test
    void Job을_조회한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces/1/jobs?page=0&size=5")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void Job을_생성한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest request = new JobCreateRequest("청소", sections);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .auth().oauth2(token)
                .when().post("/api/spaces/1/jobs")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "Job 이름이 20글자 이상일 경우 예외"})
    void Job의_이름이_1글자_미만_20글자_초과하거나_null일_경우_예외가_발생한다(final String input) {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest wrongRequest = new JobCreateRequest(input, sections);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(wrongRequest)
                .auth().oauth2(token)
                .when().post("/api/spaces/1/jobs")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "Section의 name이 20자 초과"})
    void Section의_이름이_1글자_미만_20글자_초과하거나_null일_경우_예외가_발생한다(String input) {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest(input, tasks));
        JobCreateRequest wrongRequest = new JobCreateRequest("청소", sections);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(wrongRequest)
                .auth().oauth2(token)
                .when().post("/api/spaces/1/jobs")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "Task의 이름이 1글자 미만 50글자 초과일 경우, Status Code 404를 반환한다"})
    void Task의_이름이_1글자_미만_50글자_초과하거나_null일_경우_예외가_발생한다(String input) {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest(input), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest wrongRequest = new JobCreateRequest("청소", sections);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(wrongRequest)
                .auth().oauth2(token)
                .when().post("/api/spaces/1/jobs")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
