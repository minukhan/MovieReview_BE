package com.example.BE.review;


import com.example.BE.movie.MovieEntity;
import com.example.BE.movie.MovieRepository;
import com.example.BE.review.dto.MovieReviewResponseDto;
import com.example.BE.review.dto.request.ReviewRequestDto;
import com.example.BE.review.dto.response.*;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public ReviewEntity createReview(int movieId, ReviewRequestDto reviewRequestDto) {
        // 영화 존재 여부 확인
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("영화를 찾을 수 없습니다."));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();  // 인증된 사용자의 username (id) 가져오기

        // username을 통해 UserEntity 조회
        UserEntity user = userRepository.findById(id);
        // ReviewEntity 객체 생성
        ReviewEntity review = ReviewEntity.builder()
                .rating(reviewRequestDto.getRating())  // 별점
                .description(reviewRequestDto.getDescription())  // 한줄평
                .content(reviewRequestDto.getContent())  // 상세내용
                .movie(movie)  // 영화
                .user(user)  // 사용자
                .createDate(LocalDateTime.now())  // 리뷰 작성일
                .build();

        if (!user.isPowerReviewer()) {
            int reviewCount = reviewRepository.countReviewsByUserWithAtLeastFiveHearts(user.getUserId());
            if (reviewCount >= 5) {
                user.setPowerReviewer(true);
                userRepository.save(user);
            }
        }
        // 리뷰 저장
        return reviewRepository.save(review);
    }

    public ReviewEntity updateReview(int reviewId, ReviewRequestDto reviewUpdateRequestDto) {
        // 리뷰 존재 여부 확인
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // 리뷰 정보 업데이트
        review.setRating(reviewUpdateRequestDto.getRating());
        review.setDescription(reviewUpdateRequestDto.getDescription());
        review.setContent(reviewUpdateRequestDto.getContent());
        review.setCreateDate(LocalDateTime.now());

        // 변경된 리뷰 저장
        return reviewRepository.save(review);
    }

    public void deleteReview(int reviewId){

        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        reviewRepository.delete(review);
    }

    public ResponseEntity<List<ReviewResponseDto>> getPowerReviewList() {
        List<UserEntity> powerUsers = userRepository.findByPowerReviewerTrue();
        List<ReviewResponseDto> responses = new ArrayList<>();
        for (UserEntity user : powerUsers) {
            List<ReviewEntity> reviews = reviewRepository.findByUserIdOrderByCreateDateDesc(user.getUserId());
            ReviewResponseDto dto = ReviewResponseDto.builder()
                    .review_id(reviews.get(0).getReviewId())
                    .movie_id(reviews.get(0).getMovie().getMovieId())
                    .user_id(user.getUserId())
                    .movie_title(reviews.get(0).getMovie().getTitle())
                    .poster_path(reviews.get(0).getMovie().getPosterPath())
                    .nickname(user.getNickname())
                    .profile_url(user.getProfile_url())
                    .content(reviews.get(0).getContent())
                    .heart_count(reviews.get(0).getReviewHeartCount())
                    .build();

            responses.add(dto);
        }
        return ResponseEntity.ok(responses);
    }
    public ResponseUserReviewGraph getUserGraph(int userId) throws Exception {

        UserEntity user = userRepository.findByUserId(userId);

        if(user == null) {
            throw new Exception("존재하지 않는 사용자입니다.");
        }

        ResponseUserReviewGraph graph = reviewRepository.getRatingCounts(userId);

        return graph;
    }

    public List<ResponseUserReviewList> getUserReviewList(int userId) throws Exception {
        UserEntity user = userRepository.findByUserId(userId);

        if(user == null) {
            throw new Exception("존재하지 않는 사용자입니다.");
        }

        List<ResponseUserReviewList> result = reviewRepository.findUserReviewsByUserId(userId);

        return result;
    }

    public ResponseReviewDetail getReviewDetail(int reviewId) throws Exception {
        ReviewEntity review = reviewRepository.findByReviewId(reviewId);

        if(review == null) {
            throw new Exception("존재하지 않는 리뷰입니다.");
        }

        ResponseReviewDetail result = new ResponseReviewDetail(review);
        return result;
    }

    public ResponseReviewDetail editReview(ResponseReviewDetail editedReview) throws Exception {
        ReviewEntity before = reviewRepository.findByReviewId(editedReview.getReviewId());

        if(before == null) {
            throw new Exception("존재하지 않는 리뷰입니다.");
        }

        before.setRating(editedReview.getRating());
        before.setDescription(editedReview.getDescription());
        before.setContent(editedReview.getContent());
        before.setCreateDate(editedReview.getCreateDate());

        ReviewEntity after = reviewRepository.save(before);

        ResponseReviewDetail result = new ResponseReviewDetail(after);

        return result;
    }

    public List<ResponseReviewPoster> getPosterList(int userId) {
        List<ReviewEntity> list = reviewRepository.findPosterByUserId(userId);
        int size = list.size();
        List<ResponseReviewPoster> result = new ArrayList<>();

        for(int i = 0; i < size; i++){
            result.add(new ResponseReviewPoster(list.get(i)));
        }

        return result;
    }

    public List<MovieReviewResponseDto> getLatestReviewsByMovieId(int movieId) {
        List<ReviewEntity> reviews = reviewRepository.findLatestReviewsByMovieId(movieId);

        // ReviewEntity를 ReviewResponseDto로 변환
        return reviews.stream()
                .map(review -> MovieReviewResponseDto.builder()
                        .reviewId(review.getReviewId())
                        .movieTitle(review.getMovie().getTitle()) // 영화 제목 가져오기
                        .posterPath(review.getMovie().getPosterPath()) // 영화 포스터 경로 추가
                        .nickname(review.getUser().getNickname())
                        .profileUrl(review.getUser().getProfile_url())
                        .rating(review.getRating())
                        .description(review.getDescription())
                        .content(review.getContent())
                        .createDate(review.getCreateDate())
                        .likeCount(review.getReviewHeartCount())
                        .build())
                .collect(Collectors.toList());
    }

    public List<MovieReviewResponseDto> getFavoriteReviewsByMovieId(int movieId) {
        List<ReviewEntity> reviews = reviewRepository.findFavoriteReviewsByMovieId(movieId);
        // ReviewEntity를 ReviewResponseDto로 변환
        return reviews.stream()
                .map(review -> MovieReviewResponseDto.builder()
                        .reviewId(review.getReviewId())
                        .movieTitle(review.getMovie().getTitle())
                        .posterPath(review.getMovie().getPosterPath())
                        .nickname(review.getUser().getNickname())
                        .profileUrl(review.getUser().getProfile_url())
                        .rating(review.getRating())
                        .description(review.getDescription())
                        .content(review.getContent())
                        .createDate(review.getCreateDate())
                        .likeCount(review.getReviewHeartCount()) // 좋아요 수 추가
                        .build())
                .collect(Collectors.toList());
    }

    public String getPreference(String userId) {
        Pageable top10 = PageRequest.of(0, 10);
        List<MovieEntity> movies = reviewRepository.findTop10(userId, top10);

        if(movies.isEmpty()) {
            return null;
        }

        String result = "내가 선호하는 영화들과 관련된 키워드 8개를 보여주려고 하는데, " +
                "내가 선호하는 영화 리스트를 줄테니까 그거보고 키워드 8개를 만들어줄래?" +
                " 선호하는 영화는";

        for(MovieEntity movie : movies) {
            result += movie.getTitle() + ", ";
        }

        result += " 이렇게야. 응답은 json 형태로 줄래? 키값은 keyword1, keyword2 이런식으로 보내줘";

        return result;
    }
}
