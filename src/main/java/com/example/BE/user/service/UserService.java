package com.example.BE.user.service;

import com.example.BE.review.dto.response.SimpleReviewResponseDTO;
import com.example.BE.user.UserEntity;
import com.example.BE.user.dto.EditedUserRequestDto;
import com.example.BE.user.dto.EditedUserResponseDto;
import com.example.BE.user.dto.ResponseUserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

public interface UserService {
    UserEntity findById(String id);
    UserEntity findByUserId(int user_id);
    void updateUser(UserEntity user);
    public List<SimpleReviewResponseDTO>getUserLatestReviews(int userId);
    EditedUserResponseDto editUser(String userId, String nickname, MultipartFile profileImg) throws IOException;
    ResponseUserInfo getUser(String id);
}