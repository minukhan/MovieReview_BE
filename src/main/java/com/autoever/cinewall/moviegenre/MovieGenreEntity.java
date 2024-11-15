package com.autoever.cinewall.moviegenre;

import com.autoever.cinewall.genre.GenreEntity;
import com.autoever.cinewall.movie.MovieEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "moviegenre")
public class MovieGenreEntity {

    @Id
    @Column(name="movieGenre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieGenreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEntity genre;
}
