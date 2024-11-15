package com.example.BE.follower.service;

import com.example.BE.follower.dto.response.FollowerResponseDto;
import com.example.BE.follower.dto.response.FollowingResponseDto;
import com.example.BE.user.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowerService {
    ResponseEntity<? super List<FollowingResponseDto>> getFollowingList(int user_id);
    ResponseEntity<? super List<FollowerResponseDto>> getFollowerList(int user_id);
    ResponseEntity<Integer> add_follower(UserEntity from_user, UserEntity to_user);
    ResponseEntity<Integer> remove_follower(int follower_id);
}
