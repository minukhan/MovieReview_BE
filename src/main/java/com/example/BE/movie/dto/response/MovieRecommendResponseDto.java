package com.example.BE.movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MovieRecommendResponseDto {
    int movie_id;
    String poster_path;
    double vote_average;
    double user_vote;
    int movie_count;
}