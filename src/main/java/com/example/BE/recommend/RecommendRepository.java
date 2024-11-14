package com.example.BE.recommend;

import com.example.BE.moviegenre.MovieGenreEntity;
import com.example.BE.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<RecommendEntity, Integer>{
    Optional<RecommendEntity> findByUser(UserEntity user);

}
