package com.autoever.cinewall.actor.dto;

import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.movie.dto.response.MovieSummaryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RecommendedActor {

    private Long actorId;
    private String actorName;
    private String profilePath;
    private double score;
    private List<MovieSummaryDto> movies;

    public RecommendedActor(Long actorId, String actorName, String profilePath, double score) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.profilePath = profilePath;
        this.score = score;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = new ArrayList<>();
        for(MovieEntity movie: movies) {
            this.movies.add(MovieSummaryDto.fromEntity(movie));
        }
    }
}
