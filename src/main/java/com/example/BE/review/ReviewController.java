package com.example.BE.review;

import com.example.BE.review.dto.MovieReviewResponseDto;
import com.example.BE.review.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/cinewall/review")
public class  ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/user-graph/{userId}")
    public ResponseEntity getUserReviewGraph(@PathVariable("userId") int userId) {
        ResponseUserReviewGraph result = null;
        try {
            result = reviewService.getUserGraph(userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user-list/{userId}")
    public ResponseEntity getUserReviewList(@PathVariable("userId") int userId) {
        List<ResponseUserReviewList> result = null;
        try {
            result = reviewService.getUserReviewList(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{reviewId}")
    public ResponseEntity getReviewDetail(@PathVariable("reviewId") int reviewId) {
        ResponseReviewDetail result = null;
        try {
            result = reviewService.getReviewDetail(reviewId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/edit/{reviewId}")
    public ResponseEntity editReview(@PathVariable("reviewId") int reviewId,
                                     @RequestBody ResponseReviewDetail editedReview) {
        editedReview.setReviewId(reviewId);

        System.out.println(editedReview);

        ResponseReviewDetail result = null;
        try {
            result = reviewService.editReview(editedReview);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable("reviewId") int reviewId) {
        try {
            // 리뷰 삭제
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 리뷰가 없을 때
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 그 외의 예외
        }
    }

    @GetMapping("/poster-list/{userId}")
    public ResponseEntity getPosterList(@PathVariable("userId") int userId) {
        List<ResponseReviewPoster> result = reviewService.getPosterList(userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{movieId}/reviews/latest")
    public ResponseEntity<List<MovieReviewResponseDto>> getLatestReviews(@PathVariable int movieId) {
        List<MovieReviewResponseDto> reviews = reviewService.getLatestReviewsByMovieId(movieId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{movieId}/reviews/favorite")
    public ResponseEntity<List<MovieReviewResponseDto>> getFavoriteReviews(@PathVariable int movieId) {
        List<MovieReviewResponseDto> reviews = reviewService.getFavoriteReviewsByMovieId(movieId);
        return ResponseEntity.ok(reviews);
    }
}