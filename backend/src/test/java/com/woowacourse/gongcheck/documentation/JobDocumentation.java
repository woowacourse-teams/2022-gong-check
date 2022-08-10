package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import static org.springframework.restdocs.snippet.Attributes.key;

import com.woowacourse.gongcheck.auth.domain.Authority;
import com.woowacourse.gongcheck.core.application.response.JobsResponse;
import com.woowacourse.gongcheck.core.application.response.SlackUrlResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.presentation.request.JobCreateRequest;
import com.woowacourse.gongcheck.core.presentation.request.SectionCreateRequest;
import com.woowacourse.gongcheck.core.presentation.request.SlackUrlChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.TaskCreateRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class JobDocumentation extends DocumentationTest {

    @Nested
    class Job을_조회한다 {

        @Test
        void Job_조회에_성공한다() {
            Host host = Host_생성("1234", 1234L);
            Space space = Space_생성(host, "잠실");
            when(jobService.findJobs(anyLong(), anyLong())).thenReturn(
                    JobsResponse.from(List.of(
                            Job_아이디_지정_생성(1L, space, "청소"),
                            Job_아이디_지정_생성(2L, space, "마감"))
                    )
            );
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().get("/api/spaces/{spaceId}/jobs", 1)
                    .then().log().all()
                    .apply(document("jobs/list",
                            pathParameters(
                                    parameterWithName("spaceId").description("Job 목록을 조회할 Space Id")),
                            responseFields(
                                    fieldWithPath("jobs.[].id").type(JsonFieldType.NUMBER).description("Job id"),
                                    fieldWithPath("jobs.[].name").type(JsonFieldType.STRING).description("Job 이름")

                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class Job을_생성_시 {
        List<TaskCreateRequest> tasks1 = List
                .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                        new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
        List<TaskCreateRequest> tasks2 = List
                .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                        new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
        List<SectionCreateRequest> sections = List
                .of(new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks1),
                        new SectionCreateRequest("소강의실", " 소강의실 설명", "https://image.gongcheck.shop/sogang123", tasks2));

        @Test
        void Job을_생성한다() {
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));
            JobCreateRequest request = new JobCreateRequest("청소", sections);

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/api/spaces/{spaceId}/jobs", 1)
                    .then().log().all()
                    .apply(document("jobs/create/success",
                            pathParameters(
                                    parameterWithName("spaceId").description("Job을 생성할 Space Id")),
                            requestFields(
                                    fieldWithPath("name").type(JsonFieldType.STRING)
                                            .description("Job 이름")
                                            .attributes(key("length").value(10)),
                                    fieldWithPath("sections.[].name").type(JsonFieldType.STRING)
                                            .description("Section 이름")
                                            .attributes(key("length").value(10)),
                                    fieldWithPath("sections.[].description").type(JsonFieldType.STRING)
                                            .description("Section 설명")
                                            .attributes(key("length").value(128))
                                            .attributes(key("nullable").value(true)),
                                    fieldWithPath("sections.[].imageUrl").type(JsonFieldType.STRING)
                                            .description("Section Image Url")
                                            .attributes(key("nullable").value(true)),
                                    fieldWithPath("sections.[].tasks.[].name").type(JsonFieldType.STRING)
                                            .description("Task 이름")
                                            .attributes(key("length").value(10)),
                                    fieldWithPath("sections.[].tasks.[].description").type(JsonFieldType.STRING)
                                            .description("Task 설명")
                                            .attributes(key("length").value(128))
                                            .attributes(key("nullable").value(true)),
                                    fieldWithPath("sections.[].tasks.[].imageUrl").type(JsonFieldType.STRING)
                                            .description("Task Image Url")
                                            .attributes(key("nullable").value(true))
                            )
                    ))
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void Job의_이름_길이가_올바르지_않을_경우_예외가_발생한다() {
            doThrow(BusinessException.class)
                    .when(jobService)
                    .createJob(anyLong(), anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            JobCreateRequest wrongRequest = new JobCreateRequest("10자초과의이름은안돼", sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().post("/api/spaces/1/jobs")
                    .then().log().all()
                    .apply(document("jobs/create/fail/job_name_length"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void Section_이름_길이가_올바르지_않을_경우_예외가_발생한다() {
            doThrow(BusinessException.class)
                    .when(jobService)
                    .createJob(anyLong(), anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            List<SectionCreateRequest> sections = List.of(new SectionCreateRequest("10자초과의이름은안돼", "대강의실 설명",
                    "https://image.gongcheck.shop/degang123", tasks1));
            JobCreateRequest wrongRequest = new JobCreateRequest("청소", sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().post("/api/spaces/1/jobs")
                    .then().log().all()
                    .apply(document("jobs/create/fail/section_name_length"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void Task_이름_길이가_올바르지_않을_경우_예외가_발생한다() {
            doThrow(BusinessException.class)
                    .when(jobService)
                    .createJob(anyLong(), anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            List<TaskCreateRequest> tasks1 = List
                    .of(new TaskCreateRequest("10자초과의이름은안돼", "책상 닦기 설명",
                            "https://image.gongcheck.shop/checksang123"));
            List<SectionCreateRequest> sections = List
                    .of(new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks1));
            JobCreateRequest wrongRequest = new JobCreateRequest("청소", sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().post("/api/spaces/1/jobs")
                    .then().log().all()
                    .apply(document("jobs/create/fail/task_name_length"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class Job을_수정_시 {
        List<TaskCreateRequest> tasks1 = List
                .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                        new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
        List<TaskCreateRequest> tasks2 = List
                .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                        new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
        List<SectionCreateRequest> sections = List
                .of(new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks1),
                        new SectionCreateRequest("소강의실", "소강의실 설명", "https://image.gongcheck.shop/sogang123", tasks2));

        @Test
        void 성공적으로_수정한다() {
            List<TaskCreateRequest> tasks1 = List
                    .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                            new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            List<TaskCreateRequest> tasks2 = List
                    .of(new TaskCreateRequest("책상 닦기", "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"),
                            new TaskCreateRequest("칠판 닦기", "칠판 닦기 설명", "https://image.gongcheck.shop/chilpan123"));
            List<SectionCreateRequest> sections = List
                    .of(new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks1),
                            new SectionCreateRequest("소강의실", "소강의실 설명", "https://image.gongcheck.shop/sogang123",
                                    tasks2));
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            JobCreateRequest request = new JobCreateRequest("청소", sections);

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/api/jobs/{jobId}", 1)
                    .then().log().all()
                    .apply(document("jobs/change/success",
                            pathParameters(
                                    parameterWithName("jobId").description("수정할 Job Id")),
                            requestFields(
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("Job 이름")
                                            .attributes(key("length").value(10)),
                                    fieldWithPath("sections.[].name").type(JsonFieldType.STRING)
                                            .description("Section 이름")
                                            .attributes(key("length").value(10)),
                                    fieldWithPath("sections.[].description").type(JsonFieldType.STRING)
                                            .description("Section 설명")
                                            .attributes(key("length").value(128))
                                            .attributes(key("nullable").value(true)),
                                    fieldWithPath("sections.[].imageUrl").type(JsonFieldType.STRING)
                                            .description("Section Image Url")
                                            .attributes(key("nullable").value(true)),
                                    fieldWithPath("sections.[].tasks.[].name").type(JsonFieldType.STRING)
                                            .description("Task 이름")
                                            .attributes(key("length").value(10)),
                                    fieldWithPath("sections.[].tasks.[].description").type(JsonFieldType.STRING)
                                            .description("Task 설명")
                                            .attributes(key("length").value(128))
                                            .attributes(key("nullable").value(true)),
                                    fieldWithPath("sections.[].tasks.[].imageUrl").type(JsonFieldType.STRING)
                                            .description("Task Image Url")
                                            .attributes(key("nullable").value(true))
                            )
                    ))
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", "10자초과의이름은안돼"})
        void Job_이름이_1글자_미만_10글자_초과_nul_인_경우_예외가_발생한다(final String input) {
            doThrow(BusinessException.class)
                    .when(jobService)
                    .updateJob(anyLong(), anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            JobCreateRequest wrongRequest = new JobCreateRequest(input, sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().put("/api/jobs/{jobId}", 1)
                    .then().log().all()
                    .apply(document("jobs/change/fail/job_name_length"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", "10자초과의이름은안돼"})
        void Section_이름이_1글자_미만_10글자_초과_null_일_경우_예외가_발생한다(final String input) {
            doThrow(BusinessException.class)
                    .when(jobService)
                    .updateJob(anyLong(), anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            List<SectionCreateRequest> sections = List
                    .of(new SectionCreateRequest(input, "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks1));
            JobCreateRequest wrongRequest = new JobCreateRequest("청소", sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().put("/api/jobs/{jobId}", 1)
                    .then().log().all()
                    .apply(document("jobs/change/fail/section_name_length"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", "10자초과의이름은안돼"})
        void Task_이름이_1글자_미만_10글자_초과하거나_null일_경우_예외가_발생한다(final String input) {
            doThrow(BusinessException.class)
                    .when(jobService)
                    .updateJob(anyLong(), anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            List<TaskCreateRequest> tasks1 = List.of(
                    new TaskCreateRequest(input, "책상 닦기 설명", "https://image.gongcheck.shop/checksang123"));
            List<SectionCreateRequest> sections = List
                    .of(new SectionCreateRequest("대강의실", "대강의실 설명", "https://image.gongcheck.shop/degang123", tasks1));
            JobCreateRequest wrongRequest = new JobCreateRequest("청소", sections);

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(wrongRequest)
                    .when().put("/api/jobs/{jobId}", 1)
                    .then().log().all()
                    .apply(document("jobs/change/fail/task_name_length"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    void Job을_삭제한다() {
        doNothing().when(jobService).removeJob(anyLong(), anyLong());
        when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header(AUTHORIZATION, "Bearer jwt.token.here")
                .when().delete("/api/jobs/{jobId}", 1)
                .then().log().all()
                .apply(document("jobs/delete",
                        pathParameters(
                                parameterWithName("jobId").description("삭제할 Job Id"))
                ))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Job의_Slack_Url을_조회한다() {
        when(jobService.findSlackUrl(anyLong(), anyLong())).thenReturn(new SlackUrlResponse("http://slackurl.com"));
        when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        ExtractableResponse<MockMvcResponse> response = docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/jobs/{jobId}/slack", 1)
                .then().log().all()
                .apply(document("jobs/slack_url",
                        pathParameters(
                                parameterWithName("jobId").description("Slack Url을 조회할 Job Id")),
                        responseFields(
                                fieldWithPath("slackUrl").type(JsonFieldType.STRING).description("Slack Url")
                        )))
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Nested
    class SlackUrl_수정_시 {

        @Test
        void 정상적으로_수정한다() {
            doNothing().when(jobService).changeSlackUrl(anyLong(), anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            SlackUrlChangeRequest request = new SlackUrlChangeRequest("https://newslackurl.com");

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/api/jobs/{jobId}/slack", 1)
                    .then().log().all()
                    .apply(document("jobs/change_slack_url/success",
                            pathParameters(
                                    parameterWithName("jobId").description("Slack Url을 수정할 Job Id")),
                            requestFields(
                                    fieldWithPath("slackUrl").type(JsonFieldType.STRING)
                                            .description("수정할 Slack Url")
                                            .attributes(key("nullable").value(true))
                            )
                    ))
                    .statusCode(HttpStatus.NO_CONTENT.value());

        }

        @Test
        void null이_전달될_경우_예외가_발생한다() {
            doNothing().when(jobService).changeSlackUrl(anyLong(), anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
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
