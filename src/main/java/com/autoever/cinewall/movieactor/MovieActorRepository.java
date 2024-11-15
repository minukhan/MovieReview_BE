package com.autoever.cinewall.movieactor;

import com.autoever.cinewall.movie.MovieEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieActorRepository extends JpaRepository<MovieActorEntity, Long> {
    @Query("SELECT ma.movie " +
            "FROM MovieActorEntity ma " +
            "WHERE ma.actor.id = :actorId")
    List<MovieEntity> findMovieByActor(@Value("actorId") Long actorId);
}
