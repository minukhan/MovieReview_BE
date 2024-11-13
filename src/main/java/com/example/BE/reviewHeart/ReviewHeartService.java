package com.example.BE.reviewHeart;


import com.example.BE.review.ReviewEntity;
import com.example.BE.review.ReviewRepository;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewHeartService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewHeartRepository reviewHeartRepository;

    public ReviewHeartEntity addLike(int reviewId) {
        // Spring Security에서 인증된 사용자 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();  // 인증된 사용자의 username (id) 가져오기

        // username을 통해 UserEntity 조회
        UserEntity user = userRepository.findById(id);

        // reviewId로 ReviewEntity 조회
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // 중복 좋아요 방지: 좋아요가 이미 존재하는지 확인
        if (reviewHeartRepository.existsByUserAndReview(user, review)) {
            throw new RuntimeException("User has already liked this review");
        }

        // 새로운 좋아요 등록
        ReviewHeartEntity reviewHeart = ReviewHeartEntity.builder()
                .user(user)
                .review(review)
                .build();

        return reviewHeartRepository.save(reviewHeart);
    }

    public void deleteLike(int reviewId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();  // 인증된 사용자의 username (id) 가져오기

        // username을 통해 UserEntity 조회
        UserEntity user = userRepository.findById(id);
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        ReviewHeartEntity reviewHeart = reviewHeartRepository.findByUserAndReview(user, review)
                .orElseThrow(() -> new RuntimeException("Like not found for this review by the user"));

        reviewHeartRepository.delete(reviewHeart);

    }


}