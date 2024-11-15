package com.autoever.cinewall.review.dto.response;

import com.autoever.cinewall.movie.dto.response.MovieSummaryDto;
import com.autoever.cinewall.review.ReviewEntity;
import com.autoever.cinewall.user.dto.ResponseUserInfo;
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
    private ResponseUserInfo user;
    private MovieSummaryDto movie;
    private BigDecimal rating; // double 타입은 고정 소숫점이 안된다고 하여 BigDecimal 사용
    private String description;
    private String content;
    private java.time.LocalDateTime createDate;

    public ResponseReviewDetail(ReviewEntity review) {
        this.reviewId = review.getReviewId();
        this.user = new ResponseUserInfo(review.getUser());
        this.movie = MovieSummaryDto.fromEntity(review.getMovie());
        this.rating = review.getRating();
        this.description = review.getDescription();
        this.content = review.getContent();
        this.createDate = review.getCreateDate();
    }
}
