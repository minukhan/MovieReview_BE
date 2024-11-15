package com.example.BE.review.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private int review_id;
    private int movie_id;
    private int user_id;
    private String movie_title;
    private String poster_path;
    private String nickname;
    private String profile_url;
    private String content;
    private BigDecimal rating;
    private int heart_count;
}
