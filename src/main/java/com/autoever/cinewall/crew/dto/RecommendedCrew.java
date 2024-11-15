package com.autoever.cinewall.crew.dto;

import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.movie.dto.response.MovieSummaryDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendedCrew {
    private int crewId;
    private String name;
    private String profilePath;
    private double score;
    private List<MovieSummaryDto> movies;

    public RecommendedCrew(int crewId, String name, String profilePath, double score) {
        this.crewId = crewId;
        this.name = name;
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
