package com.autoever.cinewall.user.service.implement;

import com.autoever.cinewall.review.ReviewEntity;
import com.autoever.cinewall.review.ReviewRepository;
import com.autoever.cinewall.review.dto.response.SimpleReviewResponseDTO;
import com.autoever.cinewall.s3.service.ImageUploadService;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.UserRepository;
import com.autoever.cinewall.user.dto.EditedUserRequestDto;
import com.autoever.cinewall.user.dto.EditedUserResponseDto;
import com.autoever.cinewall.user.dto.ResponseUserInfo;
import com.autoever.cinewall.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public EditedUserResponseDto editUser(String id, String nickname, MultipartFile profileImg) throws IOException {

        UserEntity userEntity = userRepository.findById(id);
        UserEntity editedUser = null;

        if(userEntity == null) {
            throw new IllegalArgumentException("User not found");
        }
        if(profileImg != null){
            String profile = imageService.upload(profileImg);
            editedUser = userEntity.toBuilder()
                    .userId(userEntity.getUserId())
                    .nickname(nickname)
                    .profile_url(profile)
                    .build();
        } else{
            editedUser = userEntity.toBuilder()
                    .userId(userEntity.getUserId())
                    .nickname(nickname)
                    .build();
        }

//        userEntity.orElseThrow(() -> new RuntimeException("User not found")).setProfile_url(editedUserDto.getProfile_url());

        UserEntity response = userRepository.save(editedUser);

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
