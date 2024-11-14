package com.example.BE.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    List<GenreEntity> findAll();
    // genre_id로 GenreEntity를 찾는 메서드
    Optional<GenreEntity> findByGenreId(int genreId);
    Optional<GenreEntity> findByName(String name);
}
