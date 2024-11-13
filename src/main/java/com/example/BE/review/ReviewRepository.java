package com.example.BE.review;

import com.example.BE.review.dto.ResponseReviewDetail;
import com.example.BE.review.dto.ResponseReviewPoster;
import com.example.BE.review.dto.ResponseUserReviewGraph;
import com.example.BE.review.dto.ResponseUserReviewList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    // 특정 영화의 모든 리뷰의 평점을 가져오는 쿼리
    @Query("SELECT r.rating FROM ReviewEntity r WHERE r.movie.movieId = :movieId")
    List<BigDecimal> findRatingsByMovieId(@Param("movieId") int movieId);

    @Query("SELECT r FROM ReviewEntity r ORDER BY r.createDate DESC")
    List<ReviewEntity> findAllByCreateDateDesc();

    @Query("SELECT r.rating, COUNT(r) FROM ReviewEntity r WHERE r.movie.movieId = :movieId GROUP BY r.rating")
    List<Object[]> findRatingDistributionByMovieId(@Param("movieId") int movieId);
    List<ReviewEntity> findByUserUserIdOrderByCreateDateDesc(int userId);


    @Query(
            "SELECT new com.example.BE.review.dto.ResponseUserReviewGraph(" +
                    "CAST(SUM(CASE WHEN r.rating >= 0 AND r.rating <= 1 THEN 1 ELSE 0 END) AS int), " +
                    "CAST(SUM(CASE WHEN r.rating > 1 AND r.rating <= 2 THEN 1 ELSE 0 END) AS int), " +
                    "CAST(SUM(CASE WHEN r.rating > 2 AND r.rating <= 3 THEN 1 ELSE 0 END) AS int), " +
                    "CAST(SUM(CASE WHEN r.rating > 3 AND r.rating <= 4 THEN 1 ELSE 0 END) AS int), " +
                    "CAST(SUM(CASE WHEN r.rating > 4 AND r.rating <= 5 THEN 1 ELSE 0 END) AS int)) " +
                    "FROM ReviewEntity r WHERE r.user.userId = :userId"
    )
    ResponseUserReviewGraph getRatingCounts(@Param("userId") int userId);

    @Query("SELECT new com.example.BE.review.dto.ResponseUserReviewList(" +
            "r.reviewId, r.user, r.movie, r.rating, r.description, r.content, r.createDate) " +
            "FROM ReviewEntity r WHERE r.user.userId = :userId")
    List<ResponseUserReviewList> findUserReviewsByUserId(@Param("userId") int userId);

    @Query("SELECT r FROM ReviewEntity r WHERE r.reviewId = :reviewId")
    ReviewEntity findByReviewId(@Param("reviewId") int reviewId);

    @Query("SELECT r FROM ReviewEntity r WHERE r.user.userId = :userId")
    List<ReviewEntity> findPosterByUserId(@Param("userId") int userId);
}