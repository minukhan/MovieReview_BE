package com.example.BE.movieactor;

import com.example.BE.actor.ActorEntity;
import com.example.BE.movie.MovieEntity;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "actor_id", nullable = false)
    private ActorEntity actor;

    @Column(nullable = false)
    private String character_name; //character 가 예약어로 쓰여 character_name 으로 대체
}
