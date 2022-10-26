package com.woowacourse.gongcheck.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.core.application.response.RunningTaskResponse;
import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import com.woowacourse.gongcheck.core.presentation.request.FlipRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class FlipAcceptanceTest extends AcceptanceTest {

    private static final String CONNECTION_PATH_FORMAT = "ws://localhost:%d/ws-connect";
    private static final String SUBSCRIPTION_PATH_FORMAT = "/topic/jobs/%s";
    private static final String FLIP_PATH_FORMAT = "/app/jobs/%d/tasks/flip";


    @Test
    void RunningTask의_체크상태를_변경한다() throws Exception {
        // given
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String token = 토큰을_요청한다(guestEnterRequest);
        RunningTask를_생성한다(token);

        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ListenableFuture<StompSession> connect = stompClient.connect(String.format(CONNECTION_PATH_FORMAT, port),
                new StompSessionHandlerAdapter() {});

        StompSession stompSession = connect.get(60, TimeUnit.SECONDS);

        BlockingQueue<RunningTasksResponse> response = new LinkedBlockingDeque<>();
        StompFrameHandlerImpl<RunningTasksResponse> handler = new StompFrameHandlerImpl<>(RunningTasksResponse.class, response);
        stompSession.subscribe(String.format(SUBSCRIPTION_PATH_FORMAT, 1L), handler);

        // when
        stompSession.send(String.format(FLIP_PATH_FORMAT, 1L), new FlipRequest(1L));

        // then
        RunningTasksResponse actual = response.poll(5, TimeUnit.SECONDS);
        assertThat(actual).isNotNull();
        assertThatFirstRunningTaskChecked(actual);
    }

    private void assertThatFirstRunningTaskChecked(RunningTasksResponse runningTasksResponse) {
        RunningTaskResponse runningTaskResponse = runningTasksResponse.getSections().get(0).getTasks().get(0);
        assertThat(runningTaskResponse.isChecked()).isTrue();
    }

    private ExtractableResponse<Response> RunningTask를_생성한다(final String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().post("/api/jobs/1/runningTasks/new")
                .then().log().all()
                .extract();
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
