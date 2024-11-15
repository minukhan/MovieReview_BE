package com.autoever.cinewall.user.service;

import com.autoever.cinewall.review.dto.response.SimpleReviewResponseDTO;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.dto.EditedUserRequestDto;
import com.autoever.cinewall.user.dto.EditedUserResponseDto;
import com.autoever.cinewall.user.dto.ResponseUserInfo;

import java.io.IOException;

import java.util.List;

public interface UserService {
    UserEntity findById(String id);
    UserEntity findByUserId(int user_id);
    void updateUser(UserEntity user);
    public List<SimpleReviewResponseDTO>getUserLatestReviews(int userId);
    EditedUserResponseDto editUser(String userId, EditedUserRequestDto editedUserDto) throws IOException;
    ResponseUserInfo getUser(String id);
}