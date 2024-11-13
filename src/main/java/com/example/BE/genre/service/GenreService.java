package com.example.BE.genre.service;

import com.example.BE.genre.GenreEntity;
import com.example.BE.genre.dto.response.SurveyResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenreService {
    public ResponseEntity<? super List<SurveyResponseDto>> getSurveyList();
    public GenreEntity getGenreById(int genreId);
}
