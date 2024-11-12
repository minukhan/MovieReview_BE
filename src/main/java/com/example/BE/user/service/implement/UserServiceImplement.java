package com.example.BE.user.service.implement;

import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import com.example.BE.user.dto.EditedUserRequestDto;
import com.example.BE.user.dto.EditedUserResponseDto;
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
    public EditedUserResponseDto editUser(EditedUserRequestDto editedUserDto) {
        UserEntity userEntity = userRepository.findByUserId(editedUserDto.getUserId());

        userEntity.setNickname(editedUserDto.getNickname());
//        userEntity.orElseThrow(() -> new RuntimeException("User not found")).setProfile_url(editedUserDto.getProfile_url());

        UserEntity response = userRepository.save(userEntity);

        EditedUserResponseDto responseDto = EditedUserResponseDto.builder()
                .userId(response.getUserId())
                .nickname(response.getNickname())
                .profile_url(response.getProfile_url())
                .build();

        return responseDto;
    }
}
