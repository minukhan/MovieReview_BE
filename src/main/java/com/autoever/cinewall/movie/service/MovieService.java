package com.autoever.cinewall.movie.service;

import com.autoever.cinewall.movie.dto.response.*;
import com.autoever.cinewall.review.dto.response.ReviewResponseDto;
import com.autoever.cinewall.user.UserEntity;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

public interface MovieService {
    public ResponseEntity<List<TeaserResponseDto>> getTrailerList();
    public ResponseEntity<List<MovieResponseDto>> getLatestMovieList(int user_id);
    public ResponseEntity<List<MovieResponseDto>> getPopularList(int user_id);
    public BigDecimal getAverageRating(int movieId);
    public ResponseEntity<List<MovieResponseDto>> getFavoriteList(int user_id);
    public ResponseEntity<List<MovieRecommendResponseDto>> getRecommendList(int user_id);
    public List<MovieResponseDto> searchMoviesByTitle(String title, int user_id);
    public List<RatingCountDTO> getRoundedRatingDistribution(int movieId);
    public ResponseEntity<List<ReviewResponseDto>> getReviewList();
    public ResponseEntity<List<MovieRecommendResponseDto>> getUserBase(UserEntity user);
    public List<MovieGenreSearchDto> getMoviesByGenreName(String genreName);
    public List<MovieGenreSearchDto> getMoviesByGenres(int movieId);

    boolean isMovieFavorite(int movieId);


    String saveInitialData(String result) throws MalformedURLException, ParseException;
}
