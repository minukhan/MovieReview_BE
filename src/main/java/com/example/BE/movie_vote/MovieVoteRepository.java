package com.example.BE.movie_vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieVoteRepository extends JpaRepository<MovieVoteEntity, Integer> {
    MovieVoteEntity findByUserIdAndMovieId(int userId, int movieId);
}
