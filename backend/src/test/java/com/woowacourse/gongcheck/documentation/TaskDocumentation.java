package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask로_Task_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_아이디_지정_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.core.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.job.Job;
import com.woowacourse.gongcheck.core.domain.section.Section;
import com.woowacourse.gongcheck.core.domain.space.Space;
import com.woowacourse.gongcheck.core.domain.task.RunningTask;
import com.woowacourse.gongcheck.core.domain.task.Task;
import com.woowacourse.gongcheck.core.domain.task.Tasks;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class TaskDocumentation extends DocumentationTest {

    @Nested
    class RunningTask를_생성한다 {

        @Test
        void RunningTask_생성에_성공한다() {
            doNothing().when(taskService).createNewRunningTasks(anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().post("/api/jobs/1/runningTasks/new")
                    .then().log().all()
                    .apply(document("runningTasks/create/success"))
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void 이미_RunningTask가_존재하는데_새로운_RunningTask를_생성하려는_경우_예외가_발생한다() {
            doThrow(new BusinessException("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.")).when(taskService)
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
                    .when().get("/api/jobs/1/active")
                    .then().log().all()
                    .apply(document("runningTasks/active/success"))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class Running_Task를_조회한다 {

        @Test
        void RunningTask가_존재하면_성공적으로_조회한다() {
            Host host = Host_생성("1234", 1234L);
            Space space = Space_생성(host, "잠실");
            Job job = Job_생성(space, "청소");
            Section section1 = Section_아이디_지정_생성(1L, job, "트랙룸");
            Section section2 = Section_아이디_지정_생성(2L, job, "굿샷강의장");
            RunningTask runningTask1 = RunningTask_생성(Task_생성(section1, "책상 청소").getId(), false);
            RunningTask runningTask2 = RunningTask_생성(Task_생성(section2, "책상 청소").getId(), true);
            Task task1 = RunningTask로_Task_아이디_지정_생성(1L, runningTask1, section1, "책상 청소");
            Task task2 = RunningTask로_Task_아이디_지정_생성(2L, runningTask2, section2, "의자 청소");
            when(taskService.findRunningTasks(anyLong(), any())).thenReturn(
                    RunningTasksResponse.from(new Tasks(List.of(task1, task2)))
            );
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().get("/api/jobs/1/runningTasks")
                    .then().log().all()
                    .apply(document("runningTasks/find/success"))
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void RunningTask가_존재하지_않는_상태에서_조회하려는_경우_예외가_발생한다() {
            doThrow(new BusinessException("현재 진행중인 작업이 존재하지 않아 조회할 수 없습니다")).when(taskService)
                    .findRunningTasks(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/api/jobs/1/runningTasks")
                    .then().log().all()
                    .apply(document("runningTasks/find/fail/active"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class RunningTask의_체크상태를_변경한다 {

        @Test
        void 체크상태_변경에_성공한다() {
            doNothing().when(taskService).flipRunningTask(anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().post("/api/tasks/1/flip")
                    .then().log().all()
                    .apply(document("runningTasks/check/success"))
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 존재하지_않는_RunningTask의_체크상태를_변경하려는_경우_예외가_발생한다() {
            doThrow(new BusinessException("현재 진행 중인 작업이 아닙니다.")).when(taskService)
                    .flipRunningTask(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/tasks/1/flip")
                    .then().log().all()
                    .apply(document("runningTasks/check/fail/active"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void RunningTask의_아이디와_Host_아이디가_연관되지_않는_경우_예외가_발생한다() {
            doThrow(new NotFoundException("진행중인 작업이 존재하지 않습니다.")).when(taskService)
                    .flipRunningTask(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/tasks/1/flip")
                    .then().log().all()
                    .apply(document("runningTasks/check/fail/match"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void RunningTask와_연관된_Host가_존재하지_않는_경우_예외가_발생한다() {
            doThrow(new NotFoundException("진행중인 작업이 존재하지 않습니다.")).when(taskService)
                    .flipRunningTask(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/tasks/1/flip")
                    .then().log().all()
                    .apply(document("runningTasks/check/fail/notfound"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
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
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/jobs/1/tasks")
                .then().log().all()
                .apply(document("tasks/find/success"))
                .statusCode(HttpStatus.OK.value());
    }
}
