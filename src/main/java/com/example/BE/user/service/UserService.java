package com.example.BE.user.service;

import com.example.BE.review.ReviewEntity;
import com.example.BE.review.dto.response.SimpleReviewResponseDTO;
import com.example.BE.user.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity findById(String id);
    UserEntity findByUserId(int user_id);
    public List<SimpleReviewResponseDTO> getUserLatestReviews(int userId);
}
