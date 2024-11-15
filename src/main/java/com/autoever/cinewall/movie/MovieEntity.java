package com.autoever.cinewall.movie;

import com.autoever.cinewall.favorite.FavoriteEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "movie")
public class MovieEntity {

    @Id
    @Column(name="movie_id")
    private int movieId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String overview;

    @Column(nullable = false, name="release_date")
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

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private List<FavoriteEntity> favorites = new ArrayList<>();

    public int getFavoriteCount() {
        return favorites.size();
    }
}
