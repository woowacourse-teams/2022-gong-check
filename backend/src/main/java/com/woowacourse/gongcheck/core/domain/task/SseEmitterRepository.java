package com.woowacourse.gongcheck.core.domain.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepository {

    private final Map<Long, List<SseEmitter>> repository = new ConcurrentHashMap<>();

    public void save(final Long jobId, final SseEmitter sseEmitter) {
        if (!repository.containsKey(jobId)) {
            repository.put(jobId, new ArrayList<>());
        }
        repository.get(jobId).add(sseEmitter);
    }

    public List<SseEmitter> getEmitters(final Long jobId) {
        return repository.get(jobId);
    }

    public void deleteById(final Long jobId, final SseEmitter emitter) {
        repository.get(jobId).remove(emitter);
    }

    public void removeAll(final Long jobId, final List<SseEmitter> failed) {
        repository.get(jobId).removeAll(failed);
    }
}
