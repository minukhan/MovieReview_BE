package com.autoever.cinewall.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleReviewResponseDTO {
    private int reviewId;
    private BigDecimal rating;
    private String description;
    private String content;
    private LocalDateTime createDate;
    private int userId;

}