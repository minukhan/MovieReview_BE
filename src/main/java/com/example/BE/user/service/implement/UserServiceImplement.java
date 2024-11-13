package com.example.BE.user.service.implement;

import com.example.BE.review.ReviewEntity;
import com.example.BE.review.ReviewRepository;
import com.example.BE.review.dto.response.SimpleReviewResponseDTO;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import com.example.BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public UserEntity findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity findByUserId(int user_id) {
        return userRepository.findByUserId(user_id);
    }

    public List<SimpleReviewResponseDTO> getUserLatestReviews(int userId) {
        List<ReviewEntity> reviews = reviewRepository.findByUserUserIdOrderByCreateDateDesc(userId);

        // ReviewEntity를 SimpleReviewResponseDTO로 변환
        return reviews.stream().map(review ->
                new SimpleReviewResponseDTO(
                        review.getReviewId(),
                        review.getRating(),
                        review.getDescription(),
                        review.getContent(),
                        review.getCreateDate()
                )
        ).collect(Collectors.toList());
    }

}
