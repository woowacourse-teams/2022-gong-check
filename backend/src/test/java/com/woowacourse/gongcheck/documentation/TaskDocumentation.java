package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.JobActiveResponse;
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
                .apply(document("tasks/isActive"))
                .statusCode(HttpStatus.OK.value());
    }
}
