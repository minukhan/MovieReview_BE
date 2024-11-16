package com.autoever.cinewall.movie;

import com.autoever.cinewall.auth.provider.JwtProvider;
import com.autoever.cinewall.movie.dto.response.*;
import com.autoever.cinewall.movie.service.MovieService;
import com.autoever.cinewall.review.dto.response.ReviewResponseDto;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.autoever.cinewall.review.ReviewService;
import com.autoever.cinewall.review.dto.request.ReviewRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/cinewall/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping("/trailer")
    public ResponseEntity<List<TeaserResponseDto>> teaser() {
        return movieService.getTrailerList();
    }

    @GetMapping("/latest")
    public ResponseEntity<List<MovieResponseDto>> Latest(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = -1;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        user = userService.findById(userName);

        if(user != null) user_id = user.getUserId();

        return movieService.getLatestMovieList(user_id);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieResponseDto>> popularity(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = -1;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        user = userService.findById(userName);

        if(user != null) user_id = user.getUserId();

        return movieService.getPopularList(user_id);
    }

    @GetMapping("/{movieId}/average-rating")
    public ResponseEntity<BigDecimal> getAverageRating(@PathVariable int movieId) {
        BigDecimal averageRating = movieService.getAverageRating(movieId);
        return ResponseEntity.ok(averageRating);
    }


    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<String> createReview(@PathVariable int movieId,
                                               @RequestBody ReviewRequestDto reviewRequestDto) {
        try {
            // 리뷰 생성
            reviewService.createReview(movieId, reviewRequestDto);
            return new ResponseEntity<>("리뷰 저장 완료", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("리뷰 저장 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable int reviewId,
            @RequestBody ReviewRequestDto reviewUpdateRequestDto) {

        try {
            // 리뷰 수정
            reviewService.updateReview(reviewId, reviewUpdateRequestDto);
            return new ResponseEntity<>("리뷰 수정 완료", HttpStatus.CREATED);
        
        } catch (Exception e) {
            return new ResponseEntity<>("리뷰 수정 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable int reviewId) {
        try {
            // 리뷰 삭제
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>("리뷰 삭제 완료", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("리뷰 삭제 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/favorite")
    public ResponseEntity<List<MovieResponseDto>> favorite(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        user = userService.findById(userName);

        if(user != null) user_id = user.getUserId();
        return movieService.getFavoriteList(user_id);
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<MovieRecommendResponseDto>> recommend(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        user = userService.findById(userName);

        if(user != null) user_id = user.getUserId();

        return movieService.getRecommendList(user_id);
    }


    @GetMapping("/search")
    public List<MovieSummaryDto> searchMovies(@RequestParam String title) {
        return movieService.searchMoviesByTitle(title);
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewResponseDto>> review(HttpServletRequest request) {
        return movieService.getReviewList();

    }

    @GetMapping("/{movieId}/rating-distribution")
    public ResponseEntity<List<RatingCountDTO>> getRatingDistribution(@PathVariable int movieId) {
        List<RatingCountDTO> ratingDistribution = movieService.getRoundedRatingDistribution(movieId);
        return ResponseEntity.ok(ratingDistribution);
    }

    @GetMapping("/powerReview")
    public ResponseEntity<List<ReviewResponseDto>> powerReview(HttpServletRequest request) {
        return reviewService.getPowerReviewList();
    }

    @GetMapping("/userbase")
    public ResponseEntity<List<MovieRecommendResponseDto>> getUserBase(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity user = userService.findById(userName);
        return movieService.getUserBase(user);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<MovieGenreSearchDto>> getMoviesByGenreName(@RequestParam String genreName) {
        List<MovieGenreSearchDto> movies = movieService.getMoviesByGenreName(genreName);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieId}/similar-movies")
    public List<MovieGenreSearchDto> getMoviesByGenres(@PathVariable int movieId) {
        return movieService.getMoviesByGenres(movieId);
    }

    @GetMapping("/{movieId}/favorite")
    public ResponseEntity isMovieFavorite(@PathVariable int movieId) {
        boolean result = movieService.isMovieFavorite(movieId);

        return ResponseEntity.ok(result);
    }

}
