package com.example.BE.user.service;

import com.example.BE.review.dto.response.SimpleReviewResponseDTO;
import com.example.BE.user.UserEntity;
import com.example.BE.user.dto.EditedUserRequestDto;
import com.example.BE.user.dto.EditedUserResponseDto;
import com.example.BE.user.dto.ResponseUserInfo;

import java.io.IOException;

import java.util.List;

public interface UserService {
    UserEntity findById(String id);
    UserEntity findByUserId(int user_id);
    void updateUser(UserEntity user);
    public List<SimpleReviewResponseDTO>getUserLatestReviews(int userId);
    EditedUserResponseDto editUser(int userId, EditedUserRequestDto editedUserDto) throws IOException;
    ResponseUserInfo getUser(String id);
}