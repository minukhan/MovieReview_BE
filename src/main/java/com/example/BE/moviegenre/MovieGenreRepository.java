package com.example.BE.moviegenre;

import com.example.BE.genre.GenreEntity;
import com.example.BE.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, Integer> {
    @Query("SELECT mg.movie FROM MovieGenreEntity mg " +
            "JOIN mg.genre g " +
            "WHERE g.genreId = :genreId " +
            "ORDER BY mg.movie.movieId ASC")
    List<MovieEntity> findMoviesByGenre(@Param("genreId") int genreId);

    @Query("SELECT mg.genre FROM MovieGenreEntity mg " +
            "JOIN mg.movie m " +
            "WHERE m.movieId = :movieId ")
    List<GenreEntity> findGenresByMovie(@Param("movieId") int movieId);

    @Query("SELECT mg.genre FROM MovieGenreEntity mg WHERE mg.movie.movieId = :movieId")
    List<GenreEntity> findGenresByMovieId(@Param("movieId") int movieId);

}
