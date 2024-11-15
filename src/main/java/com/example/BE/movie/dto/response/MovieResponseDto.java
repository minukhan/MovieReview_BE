package com.example.BE.movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class MovieResponseDto {
    int movie_id;
    String movie_title;
    String poster_path;
    double vote_average;
    BigDecimal user_rating;
}
