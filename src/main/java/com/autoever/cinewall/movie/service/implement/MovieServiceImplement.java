package com.autoever.cinewall.movie.service.implement;

import com.autoever.cinewall.actor.ActorService;
import com.autoever.cinewall.crew.CrewService;
import com.autoever.cinewall.favorite.FavoriteEntity;
import com.autoever.cinewall.favorite.FavoriteRepository;
import com.autoever.cinewall.genre.GenreEntity;
import com.autoever.cinewall.genre.GenreRepository;
import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.movie.MovieRepository;
import com.autoever.cinewall.movie.dto.response.*;
import com.autoever.cinewall.movie.service.MovieService;
import com.autoever.cinewall.moviegenre.MovieGenreEntity;
import com.autoever.cinewall.moviegenre.MovieGenreRepository;
import com.autoever.cinewall.recommend.RecommendEntity;
import com.autoever.cinewall.recommend.RecommendRepository;
import com.autoever.cinewall.review.ReviewEntity;
import com.autoever.cinewall.review.ReviewRepository;
import com.autoever.cinewall.review.dto.response.ReviewResponseDto;
import com.autoever.cinewall.reviewHeart.ReviewHeartRepository;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImplement implements MovieService {
    @Value("${tmdb.key}")
    private String apiKey;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final RecommendRepository recommendRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final GenreRepository genreRepository;
    private final ReviewHeartRepository reviewHeartRepository;
    private final UserRepository userRepository;
    private final ActorService actorService;
    private final CrewService crewService;

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

            ReviewEntity reviewEntity = null;
            BigDecimal userRating = BigDecimal.valueOf(0);

            if(user_id != -1) {
                 reviewEntity = reviewRepository.findByUserIdAndMovieId(user_id, movie_id);
                 userRating = (reviewEntity != null) ? reviewEntity.getRating() : BigDecimal.valueOf(0);
            }

            MovieResponseDto dto = MovieResponseDto.builder()
                    .movie_id(movie_id)
                    .vote_average(movie.getVoteAverage())
                    .poster_path(movie.getPosterPath())
                    .user_rating(userRating)
                    .movie_title(movie.getTitle())
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

            ReviewEntity reviewEntity = null;
            BigDecimal userRating = BigDecimal.valueOf(0);
            if(user_id != -1) {
                reviewEntity = reviewRepository.findByUserIdAndMovieId(user_id, movie_id);
                userRating = (reviewEntity != null) ? reviewEntity.getRating() : BigDecimal.valueOf(0);
            }

            MovieResponseDto dto = MovieResponseDto.builder()
                    .movie_id(movie_id)
                    .vote_average(movie.getVoteAverage())
                    .poster_path(movie.getPosterPath())
                    .user_rating(userRating)
                    .movie_title(movie.getTitle())
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
            ReviewEntity reviewEntity = null;
            BigDecimal userRating = BigDecimal.valueOf(0);
            if(user_id != -1) {
                reviewEntity = reviewRepository.findByUserIdAndMovieId(user_id, favorite.getMovie().getMovieId());
                userRating = (reviewEntity != null) ? reviewEntity.getRating() : BigDecimal.valueOf(0);
            }

            MovieResponseDto dto = MovieResponseDto.builder()
                    .movie_id(favorite.getMovie().getMovieId())
                    .vote_average(favorite.getMovie().getVoteAverage())
                    .poster_path(favorite.getMovie().getPosterPath())
                    .user_rating(userRating)
                    .movie_title(favorite.getMovie().getTitle())
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
             ReviewEntity reviewEntity = null;
            BigDecimal userRating = BigDecimal.valueOf(0);
            if(user_id != -1) {
                reviewEntity = reviewRepository.findByUserIdAndMovieId(user_id, movie_id);
                userRating = (reviewEntity != null) ? reviewEntity.getRating() : BigDecimal.valueOf(0);
            }

            MovieRecommendResponseDto dto = MovieRecommendResponseDto.builder()
                    .movie_id(movie_id)
                    .vote_average(movie.getVoteAverage())
                    .poster_path(movie.getPosterPath())
                    .user_rating(userRating)
                    .movie_count(movie.getFavoriteCount())
                    .movie_title(movie.getTitle())
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
                    .reviewId(review.getReviewId())
                    .movieId(movie.getMovieId())
                    .userId(user.getUserId())
                    .title(movie.getTitle())
                    .posterPath(movie.getPosterPath())
                    .nickname(user.getNickname())
                    .profileUrl(user.getProfile_url())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .heartCount(review.getReviewHeartCount())
                    .createDate(review.getCreateDate())
                    .heart(reviewHeartRepository.existsByUserAndReview(user, review))
                    .build();
            responses.add(dto);
        }
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<MovieRecommendResponseDto>> getUserBase(UserEntity user) {
        List<ReviewEntity> reviews = reviewRepository.findByUserAndRatingGreaterThanEqual(user.getUserId());
        List<MovieEntity> review_movies = new ArrayList<>();

        //리뷰중에 평점 4점 이상인거만 add
        for(ReviewEntity review : reviews){
            if(review.getRating().compareTo(new BigDecimal("4.0")) > 0) {
                MovieEntity movie = review.getMovie();
                review_movies.add(movie);
            }
        }

        //리뷰 4개 이상의 장르 추가
        List<GenreEntity> total_genres = new ArrayList<>();
        for(MovieEntity movie : review_movies){
            List<GenreEntity> genreEntities = movieGenreRepository.findGenresByMovie(movie.getMovieId());
            for(GenreEntity genre : genreEntities){
                total_genres.add(genre);
            }
        }

        RecommendEntity recommend = recommendRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No recommendation found for this user"));
        List<GenreEntity> recommend_genres = recommend.getGenres();

        int i = 0;
        for (GenreEntity genre : total_genres) {
            if (i >= recommend_genres.size() || i >= 5) break; // recommend_genres 크기 체크 추가
            if (recommend_genres.get(i).getGenreId() != genre.getGenreId()) {
                recommend_genres.set(i, genre); // List의 특정 인덱스 값 변경
            }
            i++;
        }

        recommend.setGenres(recommend_genres);
        recommendRepository.save(recommend);

        List<MovieRecommendResponseDto> responses = new ArrayList<>();

        Set<Integer> processedMovieIds = new HashSet<>(); // 중복을 방지하기 위한 Set

        List<GenreEntity> genres = recommend.getGenres();
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

                int movie_id = movie.getMovieId();
                ReviewEntity reviewEntity = null;
                BigDecimal userRating = BigDecimal.valueOf(0);
                if(user.getUserId() != -1) {
                    reviewEntity = reviewRepository.findByUserIdAndMovieId(user.getUserId(), movie_id);
                    userRating = (reviewEntity != null) ? reviewEntity.getRating() : BigDecimal.valueOf(0);
                }

                // 추천 DTO 생성
                MovieRecommendResponseDto dto = MovieRecommendResponseDto.builder()
                        .movie_id(movie.getMovieId())
                        .vote_average(movie.getVoteAverage())
                        .poster_path(movie.getPosterPath())
                        .user_rating(userRating)
                        .movie_count(movie.getFavoriteCount())
                        .movie_title(movie.getTitle())
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

    public List<RatingCountDTO> getRoundedRatingDistribution(int movieId) {
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

        // Map을 List<RatingCount> 형식으로 변환
        List<RatingCountDTO> ratingCounts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            long count = ratingDistribution.getOrDefault(i, 0L);
            ratingCounts.add(new RatingCountDTO(i, count));
        }

        return ratingCounts;
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

    public List<MovieGenreSearchDto> getMoviesByGenres(int movieId) {
        // 1. 영화 ID로 해당 영화의 장르 목록을 가져옴
        List<GenreEntity> genres = movieGenreRepository.findGenresByMovieId(movieId);
        System.out.println("Number of genres for movie ID " + movieId + ": " + genres.size());
        // 2. 각 장르에 맞는 영화를 랜덤하게 가져와 하나의 리스트로 합침
        List<MovieEntity> movies = new ArrayList<>();

        for (GenreEntity genre : genres) {
            // 장르별로 영화를 가져와 리스트에 추가
            Pageable top5 = PageRequest.of(0,5);
            List<MovieEntity> genreMovies = movieRepository.findRandomMoviesByGenre(genre.getGenreId(), top5);

            // movies 리스트에 추가하면서 중복 제거
            for (MovieEntity movie : genreMovies) {
                if (!movies.contains(movie)) {
                    movies.add(movie);
                }
            }
        }

        // 3. MovieEntity 리스트를 MovieGenreSearchDto 리스트로 변환
        List<MovieGenreSearchDto> movieDtos = movies.stream()
                .map(movie -> new MovieGenreSearchDto(movie.getMovieId(), movie.getTitle(), movie.getPosterPath()))
                .collect(Collectors.toList());

        return movieDtos;
    }

    @Override
    public boolean isMovieFavorite(int userId, int movieId) {

        UserEntity user = userRepository.findByUserId(userId);

        if(user == null){
            return false;
        }

        boolean result = movieRepository.isMovieFavorite(user.getUserId(), movieId);

        return result;
    }

    @Transactional
    public String saveInitialData(String result) throws MalformedURLException, ParseException {
        // 영화 데이터는 json에서 results라는 키 값에 리스트로 존재하기 때문에 이를 담기 위한 JsonArray 선언
        JsonArray list = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // 데이터를 받아올 때 날짜 타입으로 받아오는 것이 없어서 String을 DATE로 변환하기 위해 사용

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
        list = (JsonArray) jsonObject.get("results");  // jsonObject에서 "results" 키에 해당하는 값을 JsonArray 타입으로 가져옴
        JsonObject contents = null;

        for (int k = 0; k < list.size(); k++) {
            contents = (JsonObject) list.get(k);

            // trailer_path는 따로 api에서 요청이 필요해서 getTrailerKey라는 메서드에서 가져오도록 구현
//            String trailerPath = getTrailerKey(Long.parseLong(contents.get("id").toString())).replaceAll("\"", "");
            // trailer 영상 데이터가 없는 영화는 사용하지 않을 것이므로 이 경우 continue 처리
//            if (trailerPath.length() == 0) {
//                continue;
//            }

            // poster가 없는 영화는 사용하지 않을 것이므로 이 경우 continue 처리
            if (contents.get("poster_path").isJsonNull()) {
                continue;
            }

//            System.out.println(dateFormat.parse(contents.get("release_date").toString().replace("\"", "")).toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate());
            // api에서 제공하는 이미지 경로는 아래의 주소 뒤에 붙여주면 사용가능한 주소가 되므로 재사용을 위해 변수 선언
            String ImgUrl = "https://image.tmdb.org/t/p/w200";
            // trailer는 유튜브 영상이므로 아래 경로 뒤에 붙여줌
//            String trailerUrl = "https://www.youtube.com/watch?v=";

            // Movie 데이터 저장
            movieRepository.save(
                    MovieEntity.builder()
                            .movieId(contents.get("id").getAsInt())
                            .title(contents.get("title").getAsString())
                            .overview(contents.get("overview").getAsString())
                            .releaseDate(dateFormat.parse(contents.get("release_date").toString().replace("\"", "")).toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate())
//                            .voteAverage(contents.get("vote_average").getAsDouble())
//                            .voteCount(contents.get("vote_count").getAsInt())
                            .voteAverage(0)
                            .voteCount(0)
                            .posterPath(ImgUrl + contents.get("poster_path").getAsString().replaceAll("\"", ""))
//                            .backdropPath(ImgUrl + contents.get("backdrop_path").getAsString().replaceAll("\"", ""))
//                            .trailerPath(trailerUrl + trailerPath)
                            .backdropPath("")
                            .trailerPath("")
                            .build()
            );

            // Genre는 GenreController에서 따로 전체 Genre를 넣을 것
            // Actor 데이터 저장(여기서 MoveActor도 저장)
            actorService.saveActor(Integer.parseInt(contents.get("id").toString()));

            // Crew 데이터 저장(여기서 MovieCrew도 저장)
            crewService.saveCrew(Integer.parseInt(contents.get("id").toString()));

            // MovieGenre 데이터 저장
            for(int i = 0; i < contents.get("genre_ids").getAsJsonArray().asList().size(); i++){
                movieGenreRepository.save(MovieGenreEntity.builder()
                        .movie(movieRepository.findById(contents.get("id").getAsInt()).orElse(null))
                        .genre(genreRepository.findById(contents.get("genre_ids").getAsJsonArray().asList().get(i).getAsInt()).orElse(null))
                        .build());
            }
        }
        return "Success!";
    }

    // trailer_path 가져오는 메서드
//    public String getTrailerKey(Long id) throws IOException {
//        String result = "";
//        String trailerURL = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + apiKey + "&language=ko";
//
//        URL url = new URL(trailerURL);
//
//        BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//
//        result = bf.readLine();
//
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
//
//        // "results" 키의 값을 JsonArray로 가져오기
//        JsonArray resultsArray = jsonObject.getAsJsonArray("results");
//        // 빈 배열인지 확인
//        if (resultsArray != null && resultsArray.size() == 0) {
//            return "";
//        } else {
//            return resultsArray.get(0).getAsJsonObject().get("key").toString();
//        }
//    }
}
