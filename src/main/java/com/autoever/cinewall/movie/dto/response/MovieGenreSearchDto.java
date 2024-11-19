package com.autoever.cinewall.movie.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieGenreSearchDto {
    private int movieId;
    private String title;
    private String posterPath;
    double vote_average;
    BigDecimal user_rating;
}
