package com.autoever.cinewall.notification;

import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final EmitterRepository emitterRepository;


    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    //emitter 생성
    public SseEmitter subscribe(int userId) {
        SseEmitter emitter = createEmitter(userId);

        sendToClient(userId, "EventStream Created. [userId="+ userId + "]", "sse 접속 성공1");
        return emitter;
    }

    //emitter custom 알림 설정
    public <T> void customNotify(int userId, T data, String comment, String type) {
        sendToClient(userId, data, comment, type);
    }

    //emitter 알림 설정
    public void notify(int userId, Object data, String comment) {
        sendToClient(userId, data, comment);
    }

    //emitter client에게 전송
    private void sendToClient(int userId, Object data, String comment) {
        SseEmitter emitter = emitterRepository.get(userId);
        if (emitter != null) {
            CompletableFuture.runAsync(()->{
                try {
                    emitter.send(SseEmitter.event()
                            .id(String.valueOf(userId))
                            .name("sse")
                            .data(data)
                            .comment(comment));
                } catch (IOException e) {
                    emitterRepository.deleteById(userId);
                    emitter.completeWithError(e);
                }

            });
        }
    }

    //emitter client에게 전송(type 변환)
    private <T> void sendToClient(int userId, T data, String comment, String type) {
        SseEmitter emitter = emitterRepository.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(userId))
                        .name(type)
                        .data(data)
                        .comment(comment));
            } catch (IOException e) {
                emitterRepository.deleteById(userId);
                emitter.completeWithError(e);
            }
        }
    }

    @Scheduled(fixedRate = 1000)  // 1초마다 실행되는 scheduled task
    public void sendPeriodicSse() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity user = userRepository.findById(userName);
        int user_id = user.getUserId();
        SseEmitter emitter = emitterRepository.get(user_id);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("sse")
                        .data("This is a periodic message!")
                        .comment("Message every 1 second"));
            } catch (IOException e) {
                emitterRepository.deleteById(user_id);
                emitter.completeWithError(e);
            }
        }
    }

    //emitter 생성
    private SseEmitter createEmitter(int userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));

        return emitter;
    }

    private UserEntity validUser(int userId) {
        return userRepository.findByUserId(userId);
    }
}