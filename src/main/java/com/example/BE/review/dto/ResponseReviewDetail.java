package com.example.BE.review.dto;

import com.example.BE.movie.MovieEntity;
import com.example.BE.review.ReviewEntity;
import com.example.BE.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseReviewDetail {
    private int reviewId;
    private UserEntity user;
    private MovieEntity movie;
    private BigDecimal rating; // double 타입은 고정 소숫점이 안된다고 하여 BigDecimal 사용
    private String description;
    private String content;
    private java.time.LocalDateTime createDate;

    public ResponseReviewDetail(ReviewEntity review) {
        this.reviewId = review.getReviewId();
        this.user = review.getUser();
        this.movie = review.getMovie();
        this.rating = review.getRating();
        this.description = review.getDescription();
        this.content = review.getContent();
        this.createDate = review.getCreateDate();
    }
}
