package com.example.BE.follower.service;

import com.example.BE.follower.FollowerEntity;
import com.example.BE.follower.dto.response.FollowingResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowerService {
    ResponseEntity<? super List<FollowingResponseDto>> getFollowingList(int user_id);

}
