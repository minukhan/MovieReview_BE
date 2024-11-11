package com.example.BE.follower.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowingResponseDto {
    int user_id;
    String nickname;
    String profile_url;
}
