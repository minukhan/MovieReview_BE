package com.example.BE.actor;

import com.example.BE.actor.dto.RecommendedActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/cinewall/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/{userId}/recommend")
    public ResponseEntity recommendActor(@PathVariable int userId) {

        List<RecommendedActor> result = null;
        try {
            result = actorService.recommendActor(userId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}
