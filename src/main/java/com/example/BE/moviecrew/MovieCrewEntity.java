package com.example.BE.moviecrew;

import com.example.BE.movie.MovieEntity;
import com.example.BE.crew.CrewEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "moviecrew")
public class MovieCrewEntity {

    @Id
    @Column(name="movieCrew_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieCrewId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "crew_id", nullable = false)
    private CrewEntity crew;

    @Column(nullable = false)
    private String job;
}
