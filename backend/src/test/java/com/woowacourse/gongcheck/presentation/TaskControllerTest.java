package com.woowacourse.gongcheck.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class TaskControllerTest extends ControllerTest {

    @Test
    void 이미_존재하는_진행작업이_있는데_생성하는_경우_예외가_발생한다() {
        doThrow(new BusinessException("현재 진행중인 작업이 존재하여 새로운 작업을 생성할 수 없습니다.")).when(taskService)
                .createNewRunningTasks(anyLong(), anyLong());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/jobs/1/tasks/new")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 존재하는_진행작업이_없는데_조회하는_경우_예외가_발생한다() {
        doThrow(new BusinessException("현재 진행중인 작업이 존재하지 않아 조회할 수 없습니다")).when(taskService)
                .findRunningTasks(anyLong(), anyLong());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/jobs/1/tasks")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 진행중인_작업이_없는_경우_예외가_발생한다() {
        doThrow(new BusinessException("현재 진행 중인 작업이 아닙니다.")).when(taskService)
                .flipRunningTask(anyLong(), anyLong());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/tasks/1/flip")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 진행중인_작업과_hostId가_일치하지_않는_경우_예외가_발생한다() {
        doThrow(new NotFoundException("진행중인 작업이 존재하지 않습니다.")).when(taskService)
                .flipRunningTask(anyLong(), anyLong());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/tasks/1/flip")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 호스트가_존재하지_않는데_체크박스_상태변경_할_경우_예외가_발생한다() {
        doThrow(new NotFoundException("진행중인 작업이 존재하지 않습니다.")).when(taskService)
                .flipRunningTask(anyLong(), anyLong());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        ExtractableResponse<MockMvcResponse> response = given
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/tasks/1/flip")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
