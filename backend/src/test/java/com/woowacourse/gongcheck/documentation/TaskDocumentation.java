package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.RunningTask로_Task_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Section_아이디_지정_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Task_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class TaskDocumentation extends DocumentationTest {

    @Test
    void 새_진행_작업_생성() {
        doNothing().when(taskService).createNewRunningTasks(anyLong(), any());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all()
                .apply(document("tasks/create"))
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 작업_진행_여부_확인() {
        when(taskService.isJobActivated(anyLong(), any())).thenReturn(JobActiveResponse.from(true));
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/jobs/1/active")
                .then().log().all()
                .apply(document("tasks/active"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 진행중인_작업_조회() {
        Host host = Host_생성("1234");
        Space space = Space_생성(host, "잠실");
        Job job = Job_생성(space, "청소");
        Section section1 = Section_아이디_지정_생성(1L, job, "트랙룸");
        Section section2 = Section_아이디_지정_생성(2L, job, "굿샷강의장");
        RunningTask runningTask1 = RunningTask_생성(Task_생성(section1, "책상 청소"));
        RunningTask runningTask2 = RunningTask_생성(Task_생성(section2, "책상 청소"));
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
                .apply(document("tasks/find"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 진행중인_단일_작업_체크() {
        doNothing().when(taskService).flipRunningTask(anyLong(), any());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().post("/api/tasks/1/flip")
                .then().log().all()
                .apply(document("tasks/check"))
                .statusCode(HttpStatus.OK.value());
    }
}
