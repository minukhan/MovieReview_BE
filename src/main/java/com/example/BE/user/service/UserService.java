package com.example.BE.user.service;

import com.example.BE.user.UserEntity;

public interface UserService {
    UserEntity findById(String id);
}
