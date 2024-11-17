package com.autoever.cinewall.recommend;

import com.autoever.cinewall.genre.GenreEntity;
import com.autoever.cinewall.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendRepository extends JpaRepository<RecommendEntity, Integer>{
    Optional<RecommendEntity> findByUser(UserEntity user);
}
