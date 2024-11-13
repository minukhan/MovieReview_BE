package com.example.BE.movie;
;
//import com.example.BE.actor.ActorService;
//import com.example.BE.crew.CrewService;
//import com.example.BE.genre.GenreService;
import com.example.BE.auth.provider.JwtProvider;
import com.example.BE.movie.dto.response.MovieRecommendResponseDto;
import com.example.BE.movie.dto.response.MovieResponseDto;
import com.example.BE.movie.dto.response.MovieSummaryDto;
import com.example.BE.movie.dto.response.TeaserResponseDto;
import com.example.BE.movie.service.MovieService;
import com.example.BE.review.dto.response.ReviewResponseDto;
import com.example.BE.user.UserEntity;
import com.example.BE.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.example.BE.genre.GenreService;
import com.example.BE.review.ReviewEntity;
import com.example.BE.review.ReviewService;
import com.example.BE.review.dto.ReviewRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<MovieResponseDto>> teaserLatest(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = 0;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
            }
//
            // 1. Cookie에서 token 추출
            String token = jwtProvider.getTokenFromCookies(request);
//
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 2. JwtProvider를 사용해 userId 추출
            String id = jwtProvider.validate(token);
            System.out.println(id);
            if (id == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 3. userId로 DB에서 사용자 정보 조회
            user = userService.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            user = null;
        }

        if(user != null) user_id = user.getUserId();

        return movieService.getLatestMovieList(user_id);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieResponseDto>> popularity(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = 0;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
            }

            // 1. Cookie에서 token 추출
            String token = jwtProvider.getTokenFromCookies(request);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 2. JwtProvider를 사용해 userId 추출
            String id = jwtProvider.validate(token);
            System.out.println(id);
            if (id == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 3. userId로 DB에서 사용자 정보 조회
            user = userService.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            user = null;
        }

        if(user != null) user_id = user.getUserId();
        return movieService.getPopularList(user_id);
    }

    @GetMapping("/{movieId}/average-rating")
    public ResponseEntity<BigDecimal> getAverageRating(@PathVariable int movieId) {
        BigDecimal averageRating = movieService.getAverageRating(movieId);
        return ResponseEntity.ok(averageRating);
    }


    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewEntity> createReview(@PathVariable int movieId,
                                                     @RequestBody ReviewRequestDto reviewRequestDto,
                                                     @RequestParam int userId) {
        try {
            // 리뷰 생성
            ReviewEntity savedReview = reviewService.createReview(movieId, reviewRequestDto, userId);
            return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewEntity> updateReview(
            @PathVariable int reviewId,
            @RequestBody ReviewRequestDto reviewUpdateRequestDto) {
        try {
            // 리뷰 수정
            ReviewEntity updatedReview = reviewService.updateReview(reviewId, reviewUpdateRequestDto);
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable int reviewId) {
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

    @GetMapping("/favorite")
    public ResponseEntity<List<MovieResponseDto>> favorite(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = 0;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
            }

            // 1. Cookie에서 token 추출
            String token = jwtProvider.getTokenFromCookies(request);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 2. JwtProvider를 사용해 userId 추출
            String id = jwtProvider.validate(token);
            System.out.println(id);
            if (id == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 3. userId로 DB에서 사용자 정보 조회
            user = userService.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            user = null;
        }

        if(user != null) user_id = user.getUserId();
        return movieService.getFavoriteList(user_id);
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<MovieRecommendResponseDto>> recommend(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = 0;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
            }

            // 1. Cookie에서 token 추출
            String token = jwtProvider.getTokenFromCookies(request);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 2. JwtProvider를 사용해 userId 추출
            String id = jwtProvider.validate(token);
            System.out.println(id);
            if (id == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 3. userId로 DB에서 사용자 정보 조회
            user = userService.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            user = null;
        }

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
    public ResponseEntity<Map<Integer, Long>> getRatingDistribution(@PathVariable int movieId) {
        Map<Integer, Long> ratingDistribution = movieService.getRoundedRatingDistribution(movieId);
        return ResponseEntity.ok(ratingDistribution);
    }
}
