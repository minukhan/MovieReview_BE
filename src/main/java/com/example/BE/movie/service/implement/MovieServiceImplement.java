package com.example.BE.movie.service.implement;

import com.example.BE.favorite.FavoriteEntity;
import com.example.BE.favorite.FavoriteRepository;
import com.example.BE.genre.GenreEntity;
import com.example.BE.genre.GenreRepository;
import com.example.BE.genre.GenreEntity;
import com.example.BE.movie.MovieEntity;
import com.example.BE.movie.MovieRepository;
import com.example.BE.movie.dto.response.*;
import com.example.BE.movie.service.MovieService;
import com.example.BE.movie_vote.MovieVoteEntity;
import com.example.BE.movie_vote.MovieVoteRepository;
import com.example.BE.moviegenre.MovieGenreRepository;
import com.example.BE.recommend.RecommendEntity;
import com.example.BE.recommend.RecommendRepository;
import com.example.BE.review.ReviewEntity;
import com.example.BE.review.ReviewRepository;
import com.example.BE.review.dto.response.ReviewResponseDto;
import com.example.BE.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImplement implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieVoteRepository movieVoteRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final RecommendRepository recommendRepository;
    private final MovieGenreRepository movieGenreRepository;

    private final GenreRepository genreRepository;
    @Override
    public ResponseEntity<List<TeaserResponseDto>> getTrailerList() {
        List<MovieEntity> movies = movieRepository.findTop5ByOrderByVoteAverageDesc();
        List<TeaserResponseDto> responses = new ArrayList<>();
        for (MovieEntity movie : movies) {
            // 각 movie 객체에서 원하는 데이터를 처리
            String trailerPath = movie.getTrailerPath();
            TeaserResponseDto dto = new TeaserResponseDto(trailerPath);
            responses.add(dto);
        }

        // Movie 객체를 TeaserResponseDto로 변환
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<MovieResponseDto>> getLatestMovieList(int user_id) {
        List<MovieEntity> movies = movieRepository.findTop20ByOrderByReleaseDateDesc();
        List<MovieEntity> top20Movies = movies.stream()
                .limit(20)
                .collect(Collectors.toList());
        List<MovieResponseDto> responses = new ArrayList<>();

        for (MovieEntity movie : top20Movies) {
            // 각 movie 객체에서 원하는 데이터를 처리
            int movie_id = movie.getMovieId();
            System.out.println(movie_id);
            MovieVoteEntity movieVote = movieVoteRepository.findByUserIdAndMovieId(user_id, movie_id);
            // movieVote가 null일 경우 user_vote를 0으로 설정
            double userVote = (movieVote != null) ? movieVote.getVote() : 0;

            MovieResponseDto dto = MovieResponseDto.builder()
                    .movie_id(movie_id)
                    .vote_average(movie.getVoteAverage())
                    .poster_path(movie.getPosterPath())
                    .user_vote(userVote)
                    .build();
            responses.add(dto);
        }

        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<List<MovieResponseDto>> getPopularList(int user_id){
        List<MovieEntity> movies = movieRepository.findAllByOrderByVoteAverageDesc();
        List<MovieEntity> top20Movies = movies.stream()
                .limit(20)
                .collect(Collectors.toList());
        List<MovieResponseDto> responses = new ArrayList<>();
        for (MovieEntity movie : top20Movies) {
            // 각 movie 객체에서 원하는 데이터를 처리
            int movie_id = movie.getMovieId();
            System.out.println(movie_id);
            MovieVoteEntity movieVote = movieVoteRepository.findByUserIdAndMovieId(user_id, movie_id);
            // movieVote가 null일 경우 user_vote를 0으로 설정
            double userVote = (movieVote != null) ? movieVote.getVote() : 0;

            MovieResponseDto dto = MovieResponseDto.builder()
                    .movie_id(movie_id)
                    .vote_average(movie.getVoteAverage())
                    .poster_path(movie.getPosterPath())
                    .user_vote(userVote)
                    .build();
            responses.add(dto);
        }

        return ResponseEntity.ok(responses);

    }

    public BigDecimal getAverageRating(int movieId) {
        List<BigDecimal> ratings = reviewRepository.findRatingsByMovieId(movieId);

        if (ratings.isEmpty()) {
            return BigDecimal.ZERO; // 리뷰가 없을 경우 0 반환
        }

        BigDecimal sum = ratings.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(ratings.size()), 2, RoundingMode.HALF_UP);
    }

    public ResponseEntity<List<MovieResponseDto>> getFavoriteList(int user_id){
        List<FavoriteEntity> favorites = favoriteRepository.findAllByUser_UserId(user_id);
        List<MovieResponseDto> responses = new ArrayList<>();
        for (FavoriteEntity favorite : favorites) {
            MovieVoteEntity movieVote = movieVoteRepository.findByUserIdAndMovieId(user_id, favorite.getMovie().getMovieId());
            // movieVote가 null일 경우 user_vote를 0으로 설정
            double userVote = (movieVote != null) ? movieVote.getVote() : 0;

            MovieResponseDto dto = MovieResponseDto.builder()
                    .movie_id(favorite.getMovie().getMovieId())
                    .vote_average(favorite.getMovie().getVoteAverage())
                    .poster_path(favorite.getMovie().getPosterPath())
                    .user_vote(userVote)
                    .build();
            responses.add(dto);
        }

        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<List<MovieRecommendResponseDto>> getRecommendList(int user_id){
        List<MovieEntity> movies = movieRepository.findMoviesOrderByFavoriteCount();
        List<MovieEntity> top20Movies = movies.stream()
                .limit(20)
                .collect(Collectors.toList());

        List<MovieRecommendResponseDto> responses = new ArrayList<>();

        for (MovieEntity movie : top20Movies) {
            // 각 movie 객체에서 원하는 데이터를 처리
            int movie_id = movie.getMovieId();
            System.out.println(movie_id);
            MovieVoteEntity movieVote = movieVoteRepository.findByUserIdAndMovieId(user_id, movie_id);
            // movieVote가 null일 경우 user_vote를 0으로 설정
            double userVote = (movieVote != null) ? movieVote.getVote() : 0;

            MovieRecommendResponseDto dto = MovieRecommendResponseDto.builder()
                    .movie_id(movie_id)
                    .vote_average(movie.getVoteAverage())
                    .poster_path(movie.getPosterPath())
                    .user_vote(userVote)
                    .movie_count(movie.getFavoriteCount())
                    .build();
            responses.add(dto);
        }

        return ResponseEntity.ok(responses);
    }


    public List<MovieSummaryDto> searchMoviesByTitle(String title) {
        List<MovieEntity> movies = movieRepository.findByTitleContainingIgnoreCase(title);
        return movies.stream()
                .map(MovieSummaryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ResponseEntity<List<ReviewResponseDto>> getReviewList(){
        List<ReviewEntity> reviews = reviewRepository.findAllByCreateDateDesc();
        List<ReviewEntity> top24reviews = reviews.stream()
                .limit(24)
                .collect(Collectors.toList());

        List<ReviewResponseDto> responses = new ArrayList<>();
        for(ReviewEntity review : top24reviews){
            MovieEntity movie = review.getMovie();
            UserEntity user = review.getUser();
            ReviewResponseDto dto = ReviewResponseDto.builder()
                    .review_id(review.getReviewId())
                    .movie_id(movie.getMovieId())
                    .user_id(user.getUserId())
                    .movie_title(movie.getTitle())
                    .poster_path(movie.getPosterPath())
                    .nickname(user.getNickname())
                    .profile_url(user.getProfile_url())
                    .content(review.getContent())
                    .heart_count(review.getReviewHeartCount())
                    .build();
            responses.add(dto);
        }
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<MovieRecommendResponseDto>> getUserBase(UserEntity user) {
        List<ReviewEntity> reviews = reviewRepository.findByUserAndRatingGreaterThanEqual(user.getUserId());

        RecommendEntity recommend = recommendRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No recommendation found for this user"));
        List<GenreEntity> genres = recommend.getGenres();
        List<MovieRecommendResponseDto> responses = new ArrayList<>();

        Set<Integer> processedMovieIds = new HashSet<>(); // 중복을 방지하기 위한 Set

        for (GenreEntity genre : genres) {
            // 해당 장르에 속한 모든 영화 조회
            List<MovieEntity> movies = movieGenreRepository.findMoviesByGenre(genre.getGenreId());

            // 조회된 영화 중 최대 4개만 처리
            int movieCount = 0;
            for (MovieEntity movie : movies) {
                // 이미 처리한 영화인지 확인
                if (processedMovieIds.contains(movie.getMovieId())) {
                    continue; // 중복된 영화는 건너뜀
                }

                // 중복되지 않은 영화라면 Set에 추가
                processedMovieIds.add(movie.getMovieId());

                // 특정 사용자와 영화에 대한 평점 조회
                MovieVoteEntity movieVote = movieVoteRepository.findByUserIdAndMovieId(user.getUserId(), movie.getMovieId());
                double userVote = (movieVote != null) ? movieVote.getVote() : 0;

                // 추천 DTO 생성
                MovieRecommendResponseDto dto = MovieRecommendResponseDto.builder()
                        .movie_id(movie.getMovieId())
                        .vote_average(movie.getVoteAverage())
                        .poster_path(movie.getPosterPath())
                        .user_vote(userVote)
                        .movie_count(movie.getFavoriteCount())
                        .build();

                // DTO를 추천 목록에 추가
                responses.add(dto);

                // 처리한 영화 수가 4개가 되면 더 이상 추가하지 않음
                movieCount++;
                if (movieCount >= 4) {
                    break; // 4개가 되면 더 이상 추가하지 않음
                }
            }
        }

        return ResponseEntity.ok(responses);
    }

    public Map<Integer, Long> getRoundedRatingDistribution(int movieId) {
        List<Object[]> results = reviewRepository.findRatingDistributionByMovieId(movieId);
        Map<Integer, Long> ratingDistribution = new HashMap<>();

        // 1~5 별점 초기화
        for (int i = 1; i <= 5; i++) {
            ratingDistribution.put(i, 0L);
        }

        // 반올림하여 별점 분포 집계
        for (Object[] result : results) {
            BigDecimal ratingValue = (BigDecimal) result[0];
            int roundedRating = ratingValue.setScale(0, RoundingMode.HALF_UP).intValue(); // 소수 반올림
            long count = (long) result[1];

            // 0.5 이상일 때만 집계, 반올림 결과가 1~5 범위일 경우에만 값 집계
            if (ratingValue.compareTo(BigDecimal.valueOf(0.5)) >= 0 && roundedRating >= 1 && roundedRating <= 5) {
                ratingDistribution.put(roundedRating, ratingDistribution.get(roundedRating) + count);
            }
        }

        return ratingDistribution;
    }

    public List<MovieGenreSearchDto> getMoviesByGenreName(String genreName) {
        // 장르 이름으로 GenreEntity 조회
        GenreEntity genre = genreRepository.findByName(genreName)
                .orElseThrow(() -> new RuntimeException("Genre not found: " + genreName));

        // Genre ID를 사용하여 영화 목록 조회
        return movieRepository.findMoviesByGenreId(genre.getGenreId()).stream()
                .map(movie -> MovieGenreSearchDto.builder()
                        .movieId(movie.getMovieId())
                        .title(movie.getTitle())
                        .posterPath(movie.getPosterPath())
                        .build())
                .collect(Collectors.toList());
    }
}
