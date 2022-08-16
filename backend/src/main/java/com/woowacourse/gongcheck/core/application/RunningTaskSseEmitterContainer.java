package com.woowacourse.gongcheck.core.application;

import com.woowacourse.gongcheck.core.application.response.RunningTasksResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@Slf4j
public class RunningTaskSseEmitterContainer {

    private static final long DEFAULT_TIME_OUT_MILLIS = 10L * 60 * 1000;

    private final Map<Long, List<SseEmitter>> values = new ConcurrentHashMap<>();

    public SseEmitter createEmitterWithConnectionEvent(final Long jobId, final RunningTasksResponse runningTasks) {
        SseEmitter emitter = saveByJobId(jobId);
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data(runningTasks));
        } catch (IOException e) {
            log.error("데이터 전송 중 에러가 발생했습니다. message = {}, emitterId = {}", e.getMessage(), emitter);
            deleteByJobId(jobId, emitter);
            throw new RuntimeException(e);
        }
        return emitter;
    }

    public void publishFlipEvent(final Long jobId, final RunningTasksResponse runningTasks) {
        getValuesByJobId(jobId).forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("flip")
                        .data(runningTasks));
            } catch (IOException e) {
                log.info("expired emitter. message = {}, emitterId = {}", e.getMessage(), emitter);
            }
        });
    }

    public void deleteByJobId(final Long jobId, final SseEmitter emitter) {
        List<SseEmitter> emitters = values.get(jobId);
        if (Objects.isNull(emitters)) {
            return;
        }
        emitters.remove(emitter);
    }

    public List<SseEmitter> getValuesByJobId(final Long jobId) {
        return values.get(jobId);
    }

    private SseEmitter saveByJobId(final Long jobId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIME_OUT_MILLIS);
        emitter.onCompletion(() -> deleteByJobId(jobId, emitter));
        emitter.onTimeout(() -> deleteByJobId(jobId, emitter));
        if (!values.containsKey(jobId)) {
            values.put(jobId, new CopyOnWriteArrayList<>());
        }
        values.get(jobId).add(emitter);
        return emitter;
    }
}
