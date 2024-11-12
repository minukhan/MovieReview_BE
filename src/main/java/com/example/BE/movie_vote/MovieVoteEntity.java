package com.example.BE.movie_vote;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "movieVote")
public class MovieVoteEntity {
    @Id
    @Column(name="movie_vote_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieVoteId;

    @Column(nullable = false, name="user_id")
    private int userId;

    @Column(nullable = false, name="movie_id")
    private int movieId;

    @Column(nullable = false)
    private double vote;
}
