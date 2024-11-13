package com.example.BE.review.dto;

import com.example.BE.movie.MovieEntity;
import com.example.BE.user.UserEntity;
import com.example.BE.user.dto.ResponseUserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserReviewList {
    private int reviewId;
    private ResponseUserInfo user;
    private MovieEntity movie;
    private BigDecimal rating; // double 타입은 고정 소숫점이 안된다고 하여 BigDecimal 사용
    private String description;
    private String content;
    private java.time.LocalDateTime createDate;

    public ResponseUserReviewList(int reviewId, UserEntity userEntity, MovieEntity movie,
                                  BigDecimal rating, String description, String content, java.time.LocalDateTime createDate) {
        this.reviewId = reviewId;
        this.user = new ResponseUserInfo(userEntity);
        this.movie = movie;
        this.rating = rating;
        this.description = description;
        this.content = content;
        this.createDate = createDate;
    }
}
