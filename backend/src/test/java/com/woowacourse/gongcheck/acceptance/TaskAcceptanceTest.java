package com.woowacourse.gongcheck.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.core.application.response.RunningTaskResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.application.response.TasksResponse;
import com.woowacourse.gongcheck.core.presentation.request.AllCheckRequest;
import com.woowacourse.gongcheck.core.presentation.request.FlipRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

class TaskAcceptanceTest extends AcceptanceTest {

    private static final String CONNECTION_PATH_FORMAT = "ws://localhost:%d/ws-connect";
    private static final String SUBSCRIPTION_PATH_FORMAT = "/topic/jobs/%s";
    private static final String FLIP_PATH_FORMAT = "/app/jobs/%d/tasks/flip";
    private static final String ALL_CHECK_PATH_FORMAT = "/app/jobs/%d/sections/checkAll";

    @Test
    void Task를_조회한다() {
        String token = Host_토큰을_요청한다().getToken();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/tasks")
                .then().log().all()
                .extract();
        TasksResponse taskResponse = response.as(TasksResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(taskResponse.getSections()).hasSize(2)
        );
    }

    @Test
    void RunningTask를_생성한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RunningTask를_생성한다(token);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 이미_RunningTask가_존재하는_경우_생성에_실패한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);

        ExtractableResponse<Response> response = TaskAcceptanceTest.this.RunningTask를_생성한다(token);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void RunningTask를_생성하고_존재_여부를_확인한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/active")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void RunningTask의_존재여부를_확인한다() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/jobs/1/active")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void RunningTask의_체크상태를_변경한다() throws Exception {
        // given
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);
        StompSession stompSession = 웹소켓을_연결한다();

        BlockingQueue<RunningTasksResponse> response = new LinkedBlockingDeque<>();
        StompFrameHandlerImpl<RunningTasksResponse> handler = new StompFrameHandlerImpl<>(RunningTasksResponse.class,
                response);
        stompSession.subscribe(String.format(SUBSCRIPTION_PATH_FORMAT, 1L), handler);

        // when
        stompSession.send(String.format(FLIP_PATH_FORMAT, 1L), new FlipRequest(1L));

        // then
        RunningTasksResponse actual = response.poll(5, TimeUnit.SECONDS);
        assertThat(actual).isNotNull();
        assertThatRunningTaskChecked(actual, 0, 0);
    }

    @Test
    void 모든_RunningTask의_상태를_true로_바꾼다() throws Exception {
        // given
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);
        StompSession stompSession = 웹소켓을_연결한다();

        BlockingQueue<RunningTasksResponse> response = new LinkedBlockingDeque<>();
        StompFrameHandlerImpl<RunningTasksResponse> handler = new StompFrameHandlerImpl<>(RunningTasksResponse.class,
                response);
        stompSession.subscribe(String.format(SUBSCRIPTION_PATH_FORMAT, 1L), handler);

        // when
        stompSession.send(String.format(ALL_CHECK_PATH_FORMAT, 1L), new AllCheckRequest(1L));

        // then
        RunningTasksResponse actual = response.poll(5, TimeUnit.SECONDS);
        assertThat(actual).isNotNull();
        assertThatAllRunningTasksChecked(actual, 0);
    }

    private void assertThatRunningTaskChecked(final RunningTasksResponse runningTasksResponse,
                                              final int sectionIndex,
                                              final int taskIndex) {
        RunningTaskResponse runningTaskResponse = runningTasksResponse
                .getSections().get(sectionIndex)
                .getTasks().get(taskIndex);

        assertThat(runningTaskResponse.isChecked()).isTrue();
    }

    private void assertThatAllRunningTasksChecked(final RunningTasksResponse runningTasksResponse,
                                                  final int sectionIndex) {
        List<RunningTaskResponse> tasks = runningTasksResponse.getSections()
                .get(sectionIndex).getTasks();
        for (RunningTaskResponse task : tasks) {
            assertThat(task.isChecked()).isTrue();
        }

    }


    private ExtractableResponse<Response> RunningTask를_생성한다(final String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/runningTasks/new")
                .then().log().all()
                .extract();
    }

    private StompSession 웹소켓을_연결한다() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ListenableFuture<StompSession> connect = stompClient.connect(String.format(CONNECTION_PATH_FORMAT, port),
                new StompSessionHandlerAdapter() {
                });
        StompSession stompSession = connect.get(60, TimeUnit.SECONDS);
        return stompSession;
    }

    class StompFrameHandlerImpl<T> implements StompFrameHandler {

        private final Class<T> type;
        private final BlockingQueue<T> responses;

        public StompFrameHandlerImpl(final Class<T> type, final BlockingQueue<T> responses) {
            this.type = type;
            this.responses = responses;
        }

        @Override
        public Type getPayloadType(final StompHeaders headers) {
            return type;
        }

        @Override
        public void handleFrame(final StompHeaders headers, final Object payload) {
            System.out.println(payload);
            responses.offer((T) payload);
        }
    }
}
