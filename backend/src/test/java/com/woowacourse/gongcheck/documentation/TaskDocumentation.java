package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_아이디_지정_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.woowacourse.gongcheck.auth.domain.Authority;
import com.woowacourse.gongcheck.core.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.Tasks;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import com.woowacourse.gongcheck.exception.NotFoundException;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

class TaskDocumentation extends DocumentationTest {

    @Nested
    class RunningTask를_생성한다 {

        @Test
        void RunningTask_생성에_성공한다() {
            doNothing().when(taskService).createNewRunningTasks(anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().post("/api/jobs/{jobId}/runningTasks/new", 1)
                    .then().log().all()
                    .apply(document("runningTasks/create/success",
                            pathParameters(
                                    parameterWithName("jobId").description("RunningTask를 생성할 Job Id"))
                    ))
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void 이미_RunningTask가_존재하는데_새로운_RunningTask를_생성하려는_경우_예외가_발생한다() {
            doThrow(new BusinessException("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.", ErrorCode.T001)).when(taskService)
                    .createNewRunningTasks(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/jobs/1/runningTasks/new")
                    .then().log().all()
                    .apply(document("runningTasks/create/fail/active"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class RunningTask_생성_여부를_확인한다 {

        @Test
        void RunningTask_생성_여부_확인() {
            when(taskService.isJobActivated(anyLong(), any())).thenReturn(JobActiveResponse.from(true));
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().get("/api/jobs/{jobId}/active", 1)
                    .then().log().all()
                    .apply(document("runningTasks/active/success",
                            pathParameters(
                                    parameterWithName("jobId").description("RunningTask 생성 여부를 확인할 Job Id")),
                            responseFields(
                                    fieldWithPath("active").type(JsonFieldType.BOOLEAN)
                                            .description("RunningTask 진행 여부")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Test
    void Task를_조회한다() {
        Host host = Host_생성("1234", 1234L);
        Space space = Space_생성(host, "잠실");
        Job job = Job_생성(space, "청소");
        Section section1 = Section_아이디_지정_생성(1L, job, "트랙룸");
        Section section2 = Section_아이디_지정_생성(2L, job, "굿샷강의장");
        Task task1 = Task_아이디_지정_생성(1L, section1, "책상 청소");
        Task task2 = Task_아이디_지정_생성(2L, section2, "책상 청소");
        when(taskService.findTasks(anyLong(), any())).thenReturn(
                TasksResponse.from(new Tasks(List.of(task1, task2)))
        );
        when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/jobs/{jobId}/tasks", 1)
                .then().log().all()
                .apply(document("tasks/find/success",
                        pathParameters(
                                parameterWithName("jobId").description("해당 Task를 조회할 Job Id")),
                        responseFields(
                                fieldWithPath("sections.[].id").type(JsonFieldType.NUMBER)
                                        .description("Section Id"),
                                fieldWithPath("sections.[].name").type(JsonFieldType.STRING)
                                        .description("Section 이름"),
                                fieldWithPath("sections.[].imageUrl").type(JsonFieldType.STRING)
                                        .description("Section Image Url"),
                                fieldWithPath("sections.[].description").type(JsonFieldType.STRING)
                                        .description("Section 설명"),
                                fieldWithPath("sections.[].tasks.[].id").type(JsonFieldType.NUMBER)
                                        .description("Task Id"),
                                fieldWithPath("sections.[].tasks.[].name").type(JsonFieldType.STRING)
                                        .description("Task 이름"),
                                fieldWithPath("sections.[].tasks.[].imageUrl").type(JsonFieldType.STRING)
                                        .description("Task Image Url"),
                                fieldWithPath("sections.[].tasks.[].description").type(JsonFieldType.STRING)
                                        .description("Task 설명")
                        )
                ))
                .statusCode(HttpStatus.OK.value());
    }
}
