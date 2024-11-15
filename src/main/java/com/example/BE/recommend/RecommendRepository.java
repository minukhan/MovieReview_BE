package com.example.BE.recommend;

import com.example.BE.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<RecommendEntity, Integer>{
    Optional<RecommendEntity> findByUser(UserEntity user);

}
