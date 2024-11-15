package com.autoever.cinewall.recommend;

import com.autoever.cinewall.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<RecommendEntity, Integer>{
    Optional<RecommendEntity> findByUser(UserEntity user);

}
