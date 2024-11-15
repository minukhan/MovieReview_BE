package com.autoever.cinewall.movieactor;

import com.autoever.cinewall.actor.ActorEntity;
import com.autoever.cinewall.movie.MovieEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movieactor")
public class MovieActorEntity {

    @Id
    @Column(name="movieActor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieActorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private ActorEntity actor;

    @Column(nullable = false)
    private String characterName; //character 가 예약어로 쓰여 character_name 으로 대체
}
