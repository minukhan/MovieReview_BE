package com.example.BE.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ReviewRequestDto {

    private BigDecimal rating;      // 별점
    private String description;     // 한줄평
    private String content;
    
}
