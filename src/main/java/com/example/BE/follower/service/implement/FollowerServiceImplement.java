package com.example.BE.follower.service.implement;

import com.example.BE.follower.FollowerEntity;
import com.example.BE.follower.FollowerRepository;
import com.example.BE.follower.dto.response.FollowerResponseDto;
import com.example.BE.follower.dto.response.FollowingResponseDto;
import com.example.BE.follower.service.FollowerService;
import com.example.BE.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerServiceImplement implements FollowerService {
    private final FollowerRepository followerRepository;

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
        return ResponseEntity.ok(200);
    }

    @Override
    public ResponseEntity<Integer> remove_follower(int follower_id) {
        followerRepository.deleteById(follower_id);
        return ResponseEntity.ok(200);
    }
}
