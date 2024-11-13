package com.example.BE.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    // 특정 영화의 모든 리뷰의 평점을 가져오는 쿼리
    @Query("SELECT r.rating FROM ReviewEntity r WHERE r.movie.movieId = :movieId")
    List<BigDecimal> findRatingsByMovieId(@Param("movieId") int movieId);

    @Query("SELECT r FROM ReviewEntity r ORDER BY r.createDate DESC")
    List<ReviewEntity> findAllByCreateDateDesc();
}