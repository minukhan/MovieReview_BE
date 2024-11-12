package com.example.BE.movie;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    List<MovieEntity> findTop5ByOrderByVoteAverageDesc();
    // releaseDate 기준 내림차순 정렬하여 최신 영화 리스트 가져오기

    // 최신 20개의 영화만 가져오는 쿼리
    @Query("SELECT m FROM MovieEntity m ORDER BY m.releaseDate DESC")
    List<MovieEntity>  findTop20ByOrderByReleaseDateDesc();

    @Query("SELECT m FROM MovieEntity m ORDER BY m.voteAverage DESC")
    List<MovieEntity> findAllByOrderByVoteAverageDesc();

}
