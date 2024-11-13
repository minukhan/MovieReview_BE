package com.example.BE.genre.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurveyResponseDto {
    private int genre_id;
    private int movie_id;
    private String poster_path;
}
