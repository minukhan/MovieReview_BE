package com.example.BE.favorite;

import com.example.BE.movie_vote.MovieVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {
    // userId로 모든 FavoriteEntity를 조회
    List<FavoriteEntity> findAllByUser_UserId(int userId);
}
