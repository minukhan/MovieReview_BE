package com.example.BE.follower.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowerResponseDto {
    int followerId;
    int user_id;
    String nickname;
    String profile_url;
}
