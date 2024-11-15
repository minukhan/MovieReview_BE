package com.autoever.cinewall.actor;

import com.autoever.cinewall.actor.dto.RecommendedActor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Long> {

    @Query("SELECT new com.example.BE.actor.dto.RecommendedActor( " +
            "a.id, a.name, a.profilePath, AVG((r.rating / 5) * 100) AS avgScore " +
            ") " +
            "FROM ReviewEntity r " +
            "JOIN r.movie m " +
            "JOIN MovieActorEntity ma ON ma.movie.movieId = m.movieId " +
            "JOIN ma.actor a " +
            "WHERE r.user.userId = :userId " +
            "GROUP BY a.id, a.name, a.profilePath " +
            "ORDER BY avgScore DESC ")
    List<RecommendedActor> findRecommendedActor(@Value("userId") int userId, Pageable topThree);
}
