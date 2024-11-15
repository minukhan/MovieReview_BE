package com.autoever.cinewall.reviewHeart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinewall/reviews")
@RequiredArgsConstructor
public class ReviewHeartController {

    private final ReviewHeartService reviewHeartService;

    @PostMapping("/{reviewId}/like")
    public ResponseEntity<String> addLike(@PathVariable int reviewId) {

        reviewHeartService.addLike(reviewId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Review liked successfully");
    }

    @DeleteMapping("/{reviewId}/like")
    public ResponseEntity<String> deleteLike(@PathVariable int reviewId){

        reviewHeartService.deleteLike(reviewId);

        return ResponseEntity.ok("Review like removed successfully");

    }
}
