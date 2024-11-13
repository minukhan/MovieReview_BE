package com.example.BE.user.service;

import com.example.BE.user.UserEntity;
import com.example.BE.user.dto.EditedUserRequestDto;
import com.example.BE.user.dto.EditedUserResponseDto;
import com.example.BE.user.dto.ResponseUserInfo;

import java.io.IOException;

public interface UserService {
    UserEntity findById(String id);
    UserEntity findByUserId(int user_id);

    EditedUserResponseDto editUser(int userId, EditedUserRequestDto editedUserDto) throws IOException;

    ResponseUserInfo getUser(String id);
}
