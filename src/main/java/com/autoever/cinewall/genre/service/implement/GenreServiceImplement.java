package com.autoever.cinewall.genre.service.implement;

import com.autoever.cinewall.genre.GenreEntity;
import com.autoever.cinewall.genre.GenreRepository;
import com.autoever.cinewall.genre.dto.response.SurveyResponseDto;
import com.autoever.cinewall.genre.service.GenreService;
import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.moviegenre.MovieGenreRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreServiceImplement implements GenreService {
    private final GenreRepository genreRepository;
    private final MovieGenreRepository movieGenreRepository;


    @Override
    public ResponseEntity<? super List<SurveyResponseDto>> getSurveyList() {
        List<GenreEntity> genres = genreRepository.findAll();
        List<SurveyResponseDto> responses = new ArrayList<>();
        Set<Integer> usedMovieIds = new HashSet<>(); // 중복 방지용 Set

        for (GenreEntity genre : genres) {
            List<MovieEntity> movies = movieGenreRepository.findMoviesByGenre(genre.getGenreId());

            for (MovieEntity movie : movies) {
                if (!usedMovieIds.contains(movie.getMovieId())) { // 중복 체크
                    usedMovieIds.add(movie.getMovieId()); // 사용된 영화 추가
                    SurveyResponseDto dto = SurveyResponseDto.builder()
                            .movie_id(movie.getMovieId())
                            .genre_id(genre.getGenreId())
                            .poster_path(movie.getPosterPath())
                            .build();
                    responses.add(dto);
                    break; // 장르당 첫 번째 고유 영화만 선택
                }
            }
        }
        return ResponseEntity.ok(responses);
    }

    @Override
    public GenreEntity getGenreById(int genreId) {
        return genreRepository.findByGenreId(genreId)
                .orElseThrow(() -> new RuntimeException("해당 장르를 찾을 수 없습니다: " + genreId));
    }

    @Override
    public void saveAllGenre(String result) {
        // 영화 데이터는 json에서 results라는 키 값에 리스트로 존재하기 때문에 이를 담기 위한 JsonArray 선언
        JsonArray list = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");  // 데이터를 받아올 때 날짜 타입으로 받아오는 것이 없어서 String을 DATE로 변환하기 위해 사용

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
        list = (JsonArray) jsonObject.get("genres");  // jsonObject에서 "genres" 키에 해당하는 값을 JsonArray 타입으로 가져옴
        JsonObject contents = null;
        for (int k = 0; k < list.size(); k++) {
            contents = (JsonObject) list.get(k);

            genreRepository.save(GenreEntity.builder()
                            .genreId(contents.get("id").getAsInt())
                            .name(contents.get("name").getAsString())
                    .build());
        }
    }
}
