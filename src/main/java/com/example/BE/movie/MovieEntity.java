package com.example.BE.movie;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movie")
public class MovieEntity {

    @Id
    @Column(name="movie_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String overview;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private double voteAverage;

    @Column(nullable = false)
    private int voteCount;

    @Column(nullable = false)
    private String posterPath;

    @Column(nullable = false)
    private String backdropPath;

    @Column(nullable = false)
    private String trailerPath;
}
