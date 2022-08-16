package com.woowacourse.gongcheck.core.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

class RunningTaskSseEmitterContainerTest {

    private RunningTaskSseEmitterContainer container;

    @BeforeEach
    void setUp() {
        container = new RunningTaskSseEmitterContainer();
    }

    @Test
    void SseEmitter를_생성한다() {
        SseEmitter emitter = container.createEmitterWithConnectionEvent(1L, null);

        assertThat(emitter).isNotNull();
    }

    @Test
    void SseEmitter를_생성할_때_입력받은_id와_매핑하여_저장한다() {
        Long jobId = 1L;
        SseEmitter emitter = container.createEmitterWithConnectionEvent(jobId, null);

        List<SseEmitter> emittersById = container.getValuesByJobId(jobId);

        assertThat(emittersById).containsExactly(emitter);
    }

    @Test
    void 입력받은_id와_emitter에_해당하는_emitter를_제거한다() {
        Long jobId = 1L;
        SseEmitter emitter = container.createEmitterWithConnectionEvent(jobId, null);

        container.deleteByJobId(jobId, emitter);

        List<SseEmitter> emitters = container.getValuesByJobId(jobId);
        assertThat(emitters).isEmpty();
    }
}
