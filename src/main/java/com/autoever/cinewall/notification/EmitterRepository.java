package com.autoever.cinewall.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    private final Map<Integer, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(int id, SseEmitter emitter) {
        emitters.put(id, emitter);
    }

    public void deleteById(int userId) {
        emitters.remove(userId);
    }

    public SseEmitter get(int userId) {
        return emitters.get(userId);
    }
}