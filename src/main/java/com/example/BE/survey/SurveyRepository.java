package com.example.BE.survey;

import com.example.BE.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {
    @Query("SELECT s.genre FROM SurveyEntity s WHERE s.user.userId = :userId")
    List<GenreEntity> findGenresByUserId(@Param("userId")int userId);
}
