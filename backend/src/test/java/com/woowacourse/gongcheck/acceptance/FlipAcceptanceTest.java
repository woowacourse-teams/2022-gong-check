package com.woowacourse.gongcheck.acceptance;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

class FlipAcceptanceTest extends AcceptanceTest {

    @MockBean
    private SimpMessagingTemplate template;

    private WebSocketStompClient stompClient;

    @Override
    void setUp() {
        super.setUp();
        this.stompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))
        ));
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    void name() throws Exception {
        // given
        StompSession session = stompClient
                .connect(String.format("ws://localhost:%d/connect", this.port), new StompSessionHandlerAdapter() {
                })
                .get(1, TimeUnit.SECONDS);

//        session.subscribe("/topic/1", )
        // when

        // then
    }
}
