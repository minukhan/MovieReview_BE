package com.example.BE.user.service.implement;

import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import com.example.BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserEntity findById(String id) {
        return userRepository.findById(id);
    }
}
