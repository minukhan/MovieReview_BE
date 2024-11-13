package com.example.BE.user.dto;

import com.example.BE.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserInfo {
    private int userId;
    private String id;
    private String nickname;
    private String email;
    private String profile_url;

    public ResponseUserInfo(UserEntity user) {
        this.userId = user.getUserId();
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profile_url = user.getProfile_url();
    }
}
