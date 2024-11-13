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

    @Query("SELECT r.rating, COUNT(r) FROM ReviewEntity r WHERE r.movie.movieId = :movieId GROUP BY r.rating")
    List<Object[]> findRatingDistributionByMovieId(@Param("movieId") int movieId);

    @Query("SELECT COUNT(r) FROM ReviewEntity r " +
            "WHERE r.user.userId = :userId AND SIZE(r.hearts) >= 5")
    int countReviewsByUserWithAtLeastFiveHearts(@Param("userId") int userId);

    @Query("SELECT r FROM ReviewEntity r WHERE r.user.userId = :userId ORDER BY r.createDate DESC")
    List<ReviewEntity> findByUserIdOrderByCreateDateDesc(@Param("userId") int userId);

}