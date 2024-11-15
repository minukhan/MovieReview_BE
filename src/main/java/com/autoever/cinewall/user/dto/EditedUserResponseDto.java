package com.autoever.cinewall.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditedUserResponseDto {
    private int userId;
    private String nickname;
    private String profile_url;
}
