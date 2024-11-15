package com.autoever.cinewall.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReviewRequestDto {

    private BigDecimal rating;      // 별점
    private String content;

}
