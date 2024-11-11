package com.example.BE.movieactor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieActorRepository extends JpaRepository<MovieActorEntity, Long> {
}
