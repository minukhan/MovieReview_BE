package com.example.BE.movie.service;

import com.example.BE.movie.dto.response.*;
import com.example.BE.review.dto.response.ReviewResponseDto;
import com.example.BE.user.UserEntity;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MovieService {
    public ResponseEntity<List<TeaserResponseDto>> getTrailerList();
    public ResponseEntity<List<MovieResponseDto>> getLatestMovieList(int user_id);
    public ResponseEntity<List<MovieResponseDto>> getPopularList(int user_id);
    public BigDecimal getAverageRating(int movieId);
    public ResponseEntity<List<MovieResponseDto>> getFavoriteList(int user_id);
    public ResponseEntity<List<MovieRecommendResponseDto>> getRecommendList(int user_id);
    public List<MovieSummaryDto> searchMoviesByTitle(String title);
    public Map<Integer, Long> getRoundedRatingDistribution(int movieId);
    public ResponseEntity<List<ReviewResponseDto>> getReviewList();
    public ResponseEntity<List<MovieRecommendResponseDto>> getUserBase(UserEntity user);
    public List<MovieGenreSearchDto> getMoviesByGenreName(String genreName);
    public List<MovieGenreSearchDto> getMoviesByGenres(int movieId);

//    private final GenreRepository genreRepository;
//    @Value("${tmdb.key}")
//    private String apiKey;
//
//    private final MovieRepository movieRepository;
//    private final ActorService actorService;
//    private final CrewService crewService;
//
//    @Transactional
//    public String saveInitialData(String result) throws ParseException, IOException {
//        // 영화 데이터는 json에서 results라는 키 값에 리스트로 존재하기 때문에 이를 담기 위한 JsonArray 선언
//        JsonArray list = null;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");  // 데이터를 받아올 때 날짜 타입으로 받아오는 것이 없어서 String을 DATE로 변환하기 위해 사용
//
//        log.info("서비스 시작" );
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
//        list = (JsonArray) jsonObject.get("results");  // jsonObject에서 "results" 키에 해당하는 값을 JsonArray 타입으로 가져옴
//        JsonObject contents = null;
//
//        for (int k = 0; k < list.size(); k++) {
//            contents = (JsonObject) list.get(k);
//
//            // trailer_path는 따로 api에서 요청이 필요해서 getTrailerKey라는 메서드에서 가져오도록 구현
//            String trailerPath = getTrailerKey(Long.parseLong(contents.get("id").toString())).replaceAll("\"", "");
//            // trailer 영상 데이터가 없는 영화는 사용하지 않을 것이므로 이 경우 continue 처리
//            if (trailerPath.length() == 0) {
//                continue;
//            }
//
//            // api에서 제공하는 이미지 경로는 아래의 주소 뒤에 붙여주면 사용가능한 주소가 되므로 재사용을 위해 변수 선언
//            String ImgUrl = "https://image.tmdb.org/t/p/w200";
//            // trailer는 유튜브 영상이므로 아래 경로 뒤에 붙여줌
//            String trailerUrl = "https://www.youtube.com/watch?v=";
//
//            // Movie 데이터 저장
//            movieRepository.save(
//                    MovieEntity.builder()
//                            .movieId(contents.get("id").getAsInt())
//                            .title(contents.get("title").getAsString())
//                            .overview(contents.get("overview").getAsString())
//                            .releaseDate(dateFormat.parse(contents.get("release_date").toString().replace("\"", "")).toInstant()
//                                    .atZone(ZoneId.systemDefault())
//                                    .toLocalDate())
//                            .voteAverage(contents.get("vote_average").getAsDouble())
//                            .voteCount(contents.get("vote_count").getAsInt())
//                            .posterPath(ImgUrl + contents.get("poster_path").getAsString().replaceAll("\"", ""))
//                            .backdropPath(ImgUrl + contents.get("backdrop_path").getAsString().replaceAll("\"", ""))
//                            .trailerPath(trailerUrl + trailerPath)
//                            .build()
//            );
//
//            // Genre는 GenreController에서 따로 전체 Genre를 넣을 것
//            // Actor 데이터 저장(여기서 MoveActor도 저장)
//            actorService.saveActor(Long.parseLong(contents.get("id").toString()));
//
//            // Crew 데이터 저장(여기서 MovieCrew도 저장)
//            crewService.saveCrew(Long.parseLong(contents.get("id").toString()));
//
//            // MovieGenre 데이터 저장
//            for(int i = 0; i < contents.get("genre_ids").getAsJsonArray().asList().size(); i++){
//                movieGenreRepository.save(MovieGenreEntity.builder()
//                        .movie(movieRepository.findById(contents.get("id").getAsLong()).orElse(null))
//                        .genre(genreRepository.findById(contents.get("genre_ids").getAsJsonArray().asList().get(i).getAsLong()).orElse(null))
//                        .build());
//            }
//        }
//        return "Success!";
//    }
//
//    // trailer_path 가져오는 메서드
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
