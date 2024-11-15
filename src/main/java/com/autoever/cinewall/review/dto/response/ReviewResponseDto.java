package com.autoever.cinewall.review.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private int reviewId;
    private int movieId;
    private int userId;
    private String title;
    private String posterPath;
    private String nickname;
    private String profileUrl;
    private String content;
    private BigDecimal rating;
    private int heartCount;
    private boolean heart;
    private java.time.LocalDateTime createDate;
}
