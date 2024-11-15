package com.example.BE.review.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MovieReviewResponseDto {
    private int reviewId;
    private String title;
    private BigDecimal rating;
    private String posterPath;
    private String description;
    private String nickname;        // 사용자 닉네임 추가
    private String profileUrl;
    private String content;
    private LocalDateTime createDate;
    private int heartCount;
    private boolean heart;
}