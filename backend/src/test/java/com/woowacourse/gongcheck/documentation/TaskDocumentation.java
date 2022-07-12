package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask로_Task_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.JobActiveResponse;
import com.woowacourse.gongcheck.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.job.Job;
import com.woowacourse.gongcheck.domain.section.Section;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.task.RunningTask;
import com.woowacourse.gongcheck.domain.task.Task;
import com.woowacourse.gongcheck.domain.task.Tasks;
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
    class 진행_작업을_생성한다 {

        @Test
        void 새_진행_작업_생성에_성공한다() {
            doNothing().when(taskService).createNewRunningTasks(anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().post("/api/jobs/1/tasks/new")
                    .then().log().all()
                    .apply(document("tasks/create/success"))
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void 이미_존재하는_진행작업이_있는데_생성하는_경우_예외가_발생한다() {
            doThrow(new BusinessException("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.")).when(taskService)
                    .createNewRunningTasks(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/jobs/1/tasks/new")
                    .then().log().all()
                    .apply(document("tasks/create/fail/active"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class 작업의_진행_여부를_확인한다 {

        @Test
        void 작업_진행_여부_확인() {
            when(taskService.isJobActivated(anyLong(), any())).thenReturn(JobActiveResponse.from(true));
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().get("/api/jobs/1/active")
                    .then().log().all()
                    .apply(document("tasks/active/success"))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class 진행중인_작업을_조회한다 {

        @Test
        void 진행중인_작업이_있으면_조회에_성공한다() {
            Host host = Host_생성("1234");
            Space space = Space_생성(host, "잠실");
            Job job = Job_생성(space, "청소");
            Section section1 = Section_아이디_지정_생성(1L, job, "트랙룸");
            Section section2 = Section_아이디_지정_생성(2L, job, "굿샷강의장");
            RunningTask runningTask1 = RunningTask_생성(Task_생성(section1, "책상 청소").getId(), false);
            RunningTask runningTask2 = RunningTask_생성(Task_생성(section2, "책상 청소").getId(), false);
            Task task1 = RunningTask로_Task_생성(runningTask1, section1, "책상 청소");
            Task task2 = RunningTask로_Task_생성(runningTask2, section2, "의자 청소");
            when(taskService.findRunningTasks(anyLong(), any())).thenReturn(
                    RunningTasksResponse.from(new Tasks(List.of(task1, task2)))
            );
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().get("/api/jobs/1/tasks")
                    .then().log().all()
                    .apply(document("tasks/find/success"))
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 존재하는_진행작업이_없는데_조회하는_경우_예외가_발생한다() {
            doThrow(new BusinessException("현재 진행중인 작업이 존재하지 않아 조회할 수 없습니다")).when(taskService)
                    .findRunningTasks(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/api/jobs/1/tasks")
                    .then().log().all()
                    .apply(document("tasks/find/fail/active"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class 단일_작업을_체크한다 {

        @Test
        void 진행중인_단일_작업이라면_체크에_성공한다() {
            doNothing().when(taskService).flipRunningTask(anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .when().post("/api/tasks/1/flip")
                    .then().log().all()
                    .apply(document("tasks/check/success"))
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void 진행중인_작업이_아니라면_예외가_발생한다() {
            doThrow(new BusinessException("현재 진행 중인 작업이 아닙니다.")).when(taskService)
                    .flipRunningTask(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/tasks/1/flip")
                    .then().log().all()
                    .apply(document("tasks/check/fail/active"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 진행중인_작업과_hostId가_일치하지_않는_경우_예외가_발생한다() {
            doThrow(new NotFoundException("진행중인 작업이 존재하지 않습니다.")).when(taskService)
                    .flipRunningTask(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/tasks/1/flip")
                    .then().log().all()
                    .apply(document("tasks/check/fail/match"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void 호스트가_존재하지_않는_경우_예외가_발생한다() {
            doThrow(new NotFoundException("진행중인 작업이 존재하지 않습니다.")).when(taskService)
                    .flipRunningTask(anyLong(), anyLong());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            ExtractableResponse<MockMvcResponse> response = docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/tasks/1/flip")
                    .then().log().all()
                    .apply(document("tasks/check/fail/notfound"))
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }
    }
}
