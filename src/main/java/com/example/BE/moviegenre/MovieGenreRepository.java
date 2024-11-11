package com.example.BE.moviegenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, Long> {
}
