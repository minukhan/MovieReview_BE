package com.example.BE.follower.service.implement;

import com.example.BE.follower.FollowerRepository;
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
        // `followerRepository`에서 사용자 ID를 기준으로 팔로우 리스트를 가져옵니다.
        List<UserEntity> to_lists = followerRepository.findFollowingByUserId(user_id);

        // `FollowingResponseDto` 리스트를 생성합니다.
        List<FollowingResponseDto> responseDtoList = new ArrayList<>();

        // `UserEntity` 리스트를 순회하며 DTO 리스트로 변환합니다.
        for (UserEntity user : to_lists) {
            FollowingResponseDto dto = FollowingResponseDto.builder()
                    .user_id(user.getUserId())
                    .nickname(user.getNickname())
                    .profile_url(user.getProfile_url())
                    .build();
            responseDtoList.add(dto);
        }

        // 변환된 리스트를 `ResponseEntity`로 감싸서 반환합니다.
        return ResponseEntity.ok(responseDtoList);
    }
}
