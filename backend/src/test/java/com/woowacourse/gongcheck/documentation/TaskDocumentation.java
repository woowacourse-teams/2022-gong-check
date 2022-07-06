package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

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
    void 진행중인_단일_작업_체크() {
        doNothing().when(taskService).changeRunningTaskCheckedStatus(anyLong(), any());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().post("/api/tasks/1")
                .then().log().all()
                .apply(document("tasks/check"))
                .statusCode(HttpStatus.OK.value());
    }
}
