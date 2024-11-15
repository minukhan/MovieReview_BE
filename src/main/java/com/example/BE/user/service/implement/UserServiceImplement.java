package com.example.BE.user.service.implement;

import com.example.BE.review.ReviewEntity;
import com.example.BE.review.ReviewRepository;
import com.example.BE.review.dto.response.SimpleReviewResponseDTO;
import com.example.BE.s3.service.ImageUploadService;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import com.example.BE.user.dto.EditedUserRequestDto;
import com.example.BE.user.dto.EditedUserResponseDto;
import com.example.BE.user.dto.ResponseUserInfo;
import com.example.BE.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    private final ImageUploadService imageService;

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
    public List<SimpleReviewResponseDTO> getUserLatestReviews(int userId) {
        List<ReviewEntity> reviews = reviewRepository.findByUserUserIdOrderByCreateDateDesc(userId);

        // ReviewEntity를 SimpleReviewResponseDTO로 변환
        return reviews.stream().map(review ->
                new SimpleReviewResponseDTO(
                        review.getReviewId(),
                        review.getRating(),
                        review.getDescription(),
                        review.getContent(),
                        review.getCreateDate()
                )
        ).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public EditedUserResponseDto editUser(String id, EditedUserRequestDto editedUserDto) throws IOException {

        String profileImg = imageService.upload(editedUserDto.getProfileImg());
        UserEntity userEntity = userRepository.findById(id);

        if(userEntity == null) {
            throw new IllegalArgumentException("User not found");
        }

        userEntity.setNickname(editedUserDto.getNickname());
        userEntity.setProfile_url(profileImg);
//        userEntity.orElseThrow(() -> new RuntimeException("User not found")).setProfile_url(editedUserDto.getProfile_url());

        UserEntity response = userRepository.save(userEntity);

        EditedUserResponseDto responseDto = EditedUserResponseDto.builder()
                .userId(response.getUserId())
                .nickname(response.getNickname())
                .profile_url(response.getProfile_url())
                .build();

        return responseDto;
    }

    @Override
    public ResponseUserInfo getUser(String id) {
        UserEntity userEntity = userRepository.findById(id);

        if(userEntity == null) {
            throw new IllegalArgumentException("User not found");
        }

        ResponseUserInfo user = ResponseUserInfo.builder()
                .userId(userEntity.getUserId())
                .id(userEntity.getId())
                .nickname(userEntity.getNickname())
                .profile_url(userEntity.getProfile_url())
                .email(userEntity.getEmail())
                .powerReviewer(userEntity.isPowerReviewer())
                .build();

        return user;
    }
}
