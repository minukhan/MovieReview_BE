package com.example.BE.review;

import com.example.BE.review.dto.ResponseReviewDetail;
import com.example.BE.review.dto.ResponseUserReviewGraph;
import com.example.BE.review.dto.ResponseUserReviewList;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/cinewall/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/user-graph/{id}")
    public ResponseEntity getUserReviewGraph(@PathVariable("id") int userId) {
        ResponseUserReviewGraph result = reviewService.getUserGraph(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user-list/{id}")
    public ResponseEntity getUserReviewList(@PathVariable("id") int userId) {
        List<ResponseUserReviewList> result = reviewService.getUserReviewList(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{reviewId}")
    public ResponseEntity getReviewDetail(@PathVariable("reviewId") int reviewId) {
        ResponseReviewDetail result = reviewService.getReviewDetail(reviewId);

        return ResponseEntity.ok(result);
    }
}