package com.autoever.cinewall.genre.service.implement;

import com.autoever.cinewall.genre.GenreEntity;
import com.autoever.cinewall.genre.GenreRepository;
import com.autoever.cinewall.genre.dto.response.SurveyResponseDto;
import com.autoever.cinewall.genre.service.GenreService;
import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.moviegenre.MovieGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImplement implements GenreService {
    private final GenreRepository genreRepository;
    private final MovieGenreRepository movieGenreRepository;


    @Override
    public ResponseEntity<? super List<SurveyResponseDto>> getSurveyList() {
        List<GenreEntity> genres = genreRepository.findAll();
        List<SurveyResponseDto> responses = new ArrayList<>();
        for (GenreEntity genre : genres) {
            List<MovieEntity> movies = movieGenreRepository.findMoviesByGenre(genre.getGenreId());
            if (!movies.isEmpty()) {
                MovieEntity movie = movies.get(0);  // 첫 번째 영화 선택
                SurveyResponseDto dto = SurveyResponseDto.builder()
                        .movie_id(movie.getMovieId())
                        .genre_id(genre.getGenreId())
                        .poster_path(movie.getPosterPath())
                        .build();
                responses.add(dto);
            }
        }
        return ResponseEntity.ok(responses);
    }

    @Override
    public GenreEntity getGenreById(int genreId) {
        return genreRepository.findByGenreId(genreId)
                .orElseThrow(() -> new RuntimeException("해당 장르를 찾을 수 없습니다: " + genreId));
    }
}
