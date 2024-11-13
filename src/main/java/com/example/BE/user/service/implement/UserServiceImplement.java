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

    @Override
    public UserEntity findByUserId(int user_id) {
        return userRepository.findByUserId(user_id);
    }

    @Override
    public void updateUser(UserEntity user){
        userRepository.save(user);
        return;
    }
}
