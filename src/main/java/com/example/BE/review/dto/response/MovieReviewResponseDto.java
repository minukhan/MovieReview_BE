package com.example.BE.review.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MovieReviewResponseDto {
    private String movieTitle;
    private BigDecimal rating;
    private String posterPath;
    private String description;
    private String nickname;
    private String profileUrl;
    private String content;
    private LocalDateTime createDate;
    private int likeCount;
}