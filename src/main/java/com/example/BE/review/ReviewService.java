package com.example.BE.review;


import com.example.BE.movie.MovieEntity;
import com.example.BE.movie.MovieRepository;
import com.example.BE.review.dto.*;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        // 변경된 리뷰 저장
        return reviewRepository.save(review);
    }

    public void deleteReview(int reviewId){

        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        reviewRepository.delete(review);
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
}
