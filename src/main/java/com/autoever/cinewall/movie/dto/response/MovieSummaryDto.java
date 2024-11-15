package com.autoever.cinewall.movie.dto.response;

import com.autoever.cinewall.movie.MovieEntity;
import lombok.Data;

@Data
public class MovieSummaryDto {
    private int movieId;
    private String posterPath;
    private String title;

    public static MovieSummaryDto fromEntity(MovieEntity entity) {
        MovieSummaryDto dto = new MovieSummaryDto();
        dto.setMovieId(entity.getMovieId());
        dto.setPosterPath(entity.getPosterPath());
        dto.setTitle(entity.getTitle());
        return dto;
    }
}