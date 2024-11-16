package com.autoever.cinewall.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class OtherUserDto {
    private int userId;
    private String id;
    private String nickname;
    private String email;
    private String profile_url;
    private boolean powerReviewer;
    private boolean following;

    public OtherUserDto(int userId, String id, String nickname, String email, String profile_url, boolean powerReviewer, boolean following) {
        this.userId = userId;
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.profile_url = profile_url;
        this.powerReviewer = powerReviewer;
        this.following = following;
    }
}
