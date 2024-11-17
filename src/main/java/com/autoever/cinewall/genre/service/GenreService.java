package com.autoever.cinewall.genre.service;

import com.autoever.cinewall.genre.GenreEntity;
import com.autoever.cinewall.genre.dto.response.SurveyResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenreService {
    public ResponseEntity<? super List<SurveyResponseDto>> getSurveyList();
    public GenreEntity getGenreById(int genreId);

    void saveAllGenre(String result);
}
