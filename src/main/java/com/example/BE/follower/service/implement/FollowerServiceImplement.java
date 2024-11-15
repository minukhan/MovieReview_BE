package com.example.BE.follower.service.implement;

import com.example.BE.follower.FollowerEntity;
import com.example.BE.follower.FollowerRepository;
import com.example.BE.follower.dto.response.FollowerResponseDto;
import com.example.BE.follower.dto.response.FollowerSseResponse;
import com.example.BE.follower.dto.response.FollowingResponseDto;
import com.example.BE.follower.service.FollowerService;
import com.example.BE.notification.NotificationService;
import com.example.BE.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowerServiceImplement implements FollowerService {
    private final FollowerRepository followerRepository;
    private final NotificationService notificationService;

    @Override
    public ResponseEntity<? super List<FollowingResponseDto>> getFollowingList(int user_id) {
        return ResponseEntity.ok(followerRepository.findFollowingByUserId(user_id));
    }

    @Override
    public ResponseEntity<? super List<FollowerResponseDto>> getFollowerList(int user_id) {
        return ResponseEntity.ok(followerRepository.findFollowerByUserId(user_id));
    }

    @Override
    public ResponseEntity<Integer> add_follower(UserEntity from_user, UserEntity to_user) {
        FollowerEntity followerEntity = FollowerEntity.builder()
                .toUser(to_user)
                .fromUser(from_user)
                .build();
        followerRepository.save(followerEntity);

        Optional<FollowerEntity> follower = followerRepository.getFollowerByFromUserIdAndToUserId(from_user.getUserId(), to_user.getUserId());
        FollowerSseResponse followerSseResponse = FollowerSseResponse.builder()
                .follower_id(followerEntity.getFollowerId())
                .from_user_id(from_user.getUserId())
                .from_user_nickname(from_user.getNickname())
                .from_user_profile_url(from_user.getProfile_url())
                .build();
        notificationService.customNotify(from_user.getUserId(), followerSseResponse, "회원님께서 팔로우 추가 신청을 받으셨습니다!", "follow");
        return ResponseEntity.ok(200);
    }

    @Override
    public ResponseEntity<Integer> remove_follower(int follower_id) {
        followerRepository.deleteById(follower_id);
        return ResponseEntity.ok(200);
    }
}
