package com.example.BE.review.dto.response;

import com.example.BE.movie.MovieEntity;
import com.example.BE.movie.dto.response.MovieSummaryDto;
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
    private MovieSummaryDto movie;
    private BigDecimal rating; // double 타입은 고정 소숫점이 안된다고 하여 BigDecimal 사용
    private String description;
    private String content;
    private java.time.LocalDateTime createDate;
    private long heartCount;
    private boolean heart;

    public ResponseUserReviewList(int reviewId, UserEntity userEntity, MovieEntity movie,
                                  BigDecimal rating, String description, String content,
                                  java.time.LocalDateTime createDate, long heartCount, boolean heart) {
        this.reviewId = reviewId;
        this.user = new ResponseUserInfo(userEntity);
        this.movie = MovieSummaryDto.fromEntity(movie);
        this.rating = rating;
        this.description = description;
        this.content = content;
        this.createDate = createDate;
        this.heartCount = heartCount;
        this.heart = heart;
    }
}
