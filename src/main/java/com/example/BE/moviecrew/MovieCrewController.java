package com.example.BE.moviecrew;

import com.example.BE.crew.dto.RecommendedCrew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinewall/crew")
public class MovieCrewController {

    @Autowired
    private MovieCrewService movieCrewService;

    @GetMapping("/{userId}/recommend")
    public ResponseEntity recommendCrew(@PathVariable("userId") int userId) {

        List<RecommendedCrew> result = null;
        try {
            result = movieCrewService.recommendCrew(userId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}
