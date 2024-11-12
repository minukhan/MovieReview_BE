package com.example.BE.user.service;

import com.example.BE.user.UserEntity;
import com.example.BE.user.dto.EditedUserRequestDto;
import com.example.BE.user.dto.EditedUserResponseDto;

public interface UserService {
    UserEntity findById(String id);
    UserEntity findByUserId(int user_id);

    EditedUserResponseDto editUser(EditedUserRequestDto editedUserDto);
}
