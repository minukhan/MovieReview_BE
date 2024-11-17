package com.autoever.cinewall.recommend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecommendResponseDto {
    String genre1;
    String genre2;
    String genre3;
    String genre4;
    String genre5;
}
