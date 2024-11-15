package com.autoever.cinewall.follower.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowerSseResponse {
    int follower_id;
    int from_user_id;
    String from_user_nickname;
    String from_user_profile_url;
}
