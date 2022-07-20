package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.JobsResponse;
import com.woowacourse.gongcheck.application.response.SlackUrlResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.presentation.request.SlackUrlChangeRequest;
import com.woowacourse.gongcheck.presentation.request.TaskCreateRequest;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class JobDocumentation extends DocumentationTest {

    @Nested
    class Job을_조회한다 {

        @Test
        void Job_조회에_성공한다() {
            Host host = Host_생성("1234", 1234L);
            Space space = Space_생성(host, "잠실");
            when(jobService.findPage(anyLong(), anyLong(), any())).thenReturn(
                    JobsResponse.of(List.of(
                                    Job_아이디_지정_생성(1L, space, "청소"),
                                    Job_아이디_지정_생성(2L, space, "마감")),
                            true)
            );
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .queryParam("page", 0)
                    .queryParam("size", 2)
                    .when().get("/api/spaces/1/jobs")
                    .then().log().all()
                    .apply(document("jobs/list"))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class Job을_생성_시 {
        List<TaskCreateRequest> tasks1 = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<TaskCreateRequest> tasks2 = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks1),
                new SectionCreateRequest("소강의실", tasks2));

        @Test
        void Job을_생성한다() {
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));
            JobCreateRequest request = new JobCreateRequest("청소", sections);

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/api/spaces/1/jobs")
                    .then().log().all()
                    .apply(document("jobs/list"))
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void Job의_이름_길이가_올바르지_않을_경우_예외가_발생한다() {
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));
            JobCreateRequest wrongRequest = new JobCreateRequest("작업의 이름이 20글자 초과한다면 예외", sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().post("/api/spaces/1/jobs")
                    .then().log().all()
                    .apply(document("jobs/list"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void Section_이름_길이가_올바르지_않을_경우_예외가_발생한다() {
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));
            List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("Section의 name이 20자 초과", tasks1));
            JobCreateRequest wrongRequest = new JobCreateRequest("청소", sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().post("/api/spaces/1/jobs")
                    .then().log().all()
                    .apply(document("jobs/list"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void Task_이름_길이가_올바르지_않을_경우_예외가_발생한다() {
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));
            List<TaskCreateRequest> tasks1 = List.of(
                    new TaskCreateRequest("Task의 이름이 1글자 미만 50글자 초과일 경우, Status Code 404를 반환한다"));
            List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks1));
            JobCreateRequest wrongRequest = new JobCreateRequest("청소", sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().post("/api/spaces/1/jobs")
                    .then().log().all()
                    .apply(document("jobs/list"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    void Job을_수정한다() {
        List<TaskCreateRequest> tasks1 = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<TaskCreateRequest> tasks2 = List.of(new TaskCreateRequest("책상 닦기"), new TaskCreateRequest("칠판 닦기"));
        List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("대강의실", tasks1), new SectionCreateRequest("소강의실", tasks2));
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));
        JobCreateRequest request = new JobCreateRequest("청소", sections);

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/api/jobs/1")
                .then().log().all()
                .apply(document("jobs/list"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Job을_삭제한다() {
        doNothing().when(jobService).removeJob(anyLong(), anyLong());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header(AUTHORIZATION, "Bearer jwt.token.here")
                .when().delete("/api/jobs/1")
                .then().log().all()
                .apply(document("jobs/delete"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Job의_Slack_Url을_조회한다() {
        when(jobService.findSlackUrl(anyLong(), anyLong())).thenReturn(new SlackUrlResponse("http://slackurl.com"));
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        ExtractableResponse<MockMvcResponse> response = docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/jobs/1/slack")
                .then().log().all()
                .apply(document("jobs/slack_url"))
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Nested
    class SlackUrl_수정_시 {

        @Test
        void 정상적으로_수정한다() {
            doNothing().when(jobService).changeSlackUrl(anyLong(), anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            SlackUrlChangeRequest request = new SlackUrlChangeRequest("https://newslackurl.com");

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/api/jobs/1/slack")
                    .then().log().all()
                    .apply(document("jobs/change_slack_url/success"))
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void null이_전달될_경우_예외가_발생한다() {
            doNothing().when(jobService).changeSlackUrl(anyLong(), anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            SlackUrlChangeRequest wrongRequest = new SlackUrlChangeRequest(null);

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().put("/api/jobs/1/slack")
                    .then().log().all()
                    .apply(document("jobs/change_slack_url/fail"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
