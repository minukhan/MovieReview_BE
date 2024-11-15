package com.autoever.cinewall.favorite;

import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {
    // userId로 모든 FavoriteEntity를 조회
    List<FavoriteEntity> findAllByUser_UserId(int userId);
    boolean existsByUserAndMovie(UserEntity user, MovieEntity movie);
    // 찜 항목을 찾는 메서드
    Optional<FavoriteEntity> findByUserAndMovie(UserEntity user, MovieEntity movie);
}
