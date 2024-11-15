package com.autoever.cinewall.favorite;

import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "favorite")
public class FavoriteEntity {

    @Id
    @Column(name="favorite_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favoriteId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

}
