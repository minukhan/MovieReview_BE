package com.example.BE.reviewHeart;


import com.example.BE.review.ReviewEntity;
import com.example.BE.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewHeartRepository extends JpaRepository<ReviewHeartEntity, Integer> {

    // 특정 사용자와 리뷰 조합의 좋아요가 이미 존재하는지 확인하는 메서드
    boolean existsByUserAndReview(UserEntity user, ReviewEntity review);

    // 특정 사용자와 리뷰 조합의 좋아요를 조회하는 메서드
    Optional<ReviewHeartEntity> findByUserAndReview(UserEntity user, ReviewEntity review);
}