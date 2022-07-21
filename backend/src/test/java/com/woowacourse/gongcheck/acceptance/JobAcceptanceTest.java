package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.acceptance.AuthSupport.Host_토큰을_요청한다;
import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;

import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SlackUrlChangeRequest;
import com.woowacourse.gongcheck.presentation.request.TaskCreateRequest;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class JobAcceptanceTest extends AcceptanceTest {

    @Test
    void Host_토큰으로_Job을_조회한다() {
        String token = Host_토큰을_요청한다().getToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces/1/jobs")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Host_토큰으로_Job을_생성한다() {
        String token = Host_토큰을_요청한다().getToken();

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest request = new JobCreateRequest("청소", sections);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .auth().oauth2(token)
                .when().post("/api/spaces/1/jobs")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void Host_토큰으로_Job을_수정한다() {
        String token = Host_토큰을_요청한다().getToken();

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest request = new JobCreateRequest("청소", sections);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .auth().oauth2(token)
                .when().put("/api/jobs/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Host_토큰으로_존재하지_않는_Job을_수정할_경우_예외가_발생한다() {
        String token = Host_토큰을_요청한다().getToken();

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest request = new JobCreateRequest("청소", sections);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .auth().oauth2(token)
                .when().put("/api/jobs/0")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void Host_토큰으로_Job을_삭제한다() {
        String token = Host_토큰을_요청한다().getToken();

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().delete("/api/jobs/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Host_토큰으로_Job의_Slack_Url을_조회한다() {
        String token = Host_토큰을_요청한다().getToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/slack")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Host_토큰으로_Job의_Slack_Url을_수정한다() {
        String token = Host_토큰을_요청한다().getToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SlackUrlChangeRequest("https://newslackurl.com"))
                .when().put("/api/jobs/1/slack")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Guest_토큰으로_Job을_조회한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces/1/jobs")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Guest_토큰으로_Job을_생성_시_예외가_발생한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest request = new JobCreateRequest("청소", sections);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .auth().oauth2(token)
                .when().post("/api/spaces/1/jobs")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void Guest_토큰으로_Job을_수정_시_예외가_발생한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        List<TaskCreateRequest> tasks = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks));
        JobCreateRequest request = new JobCreateRequest("청소", sections);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .auth().oauth2(token)
                .when().put("/api/jobs/1")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void Guest_토큰으로_Job을_삭제_시_예외가_발생한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().delete("/api/jobs/1")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void Guest_토큰으로_Job의_Slack_Url을_조회_시_예외가_발생한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/slack")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void Guest_토큰으로_Job의_Slack_Url을_수정_시_예외가_발생한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SlackUrlChangeRequest("https://newslackurl.com"))
                .when().put("/api/jobs/1/slack")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
