package com.autoever.cinewall.review;


import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.movie.MovieRepository;
import com.autoever.cinewall.review.dto.response.*;
import com.autoever.cinewall.review.dto.request.ReviewRequestDto;
import com.autoever.cinewall.reviewHeart.ReviewHeartRepository;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewHeartRepository reviewHeartRepository;

    @Transactional
    public ReviewEntity createReview(int movieId, ReviewRequestDto reviewRequestDto) throws Exception {
        // 영화 존재 여부 확인
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("영화를 찾을 수 없습니다."));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();  // 인증된 사용자의 username (id) 가져오기

        // username을 통해 UserEntity 조회
        UserEntity user = userRepository.findById(id);

        // 이미 작성된 리뷰가 있는지 확인
        if(reviewRepository.findByUserIdAndMovieId(user.getUserId(), movieId) != null) {
            throw new Exception("이미 리뷰를 작성한 영화입니다.");
        }

        // ReviewEntity 객체 생성
        ReviewEntity review = ReviewEntity.builder()
                .rating(reviewRequestDto.getRating())  // 별점
                .content(reviewRequestDto.getContent())  // 상세내용
                .movie(movie)  // 영화
                .user(user)  // 사용자
                .createDate(LocalDateTime.now())  // 리뷰 작성일
                .build();

        // 파워리뷰어 여부 확인 및 업데이트
        if (!user.isPowerReviewer()) {
            int reviewCount = reviewRepository.countReviewsByUserWithAtLeastFiveHearts(user.getUserId());
            if (reviewCount >= 5) {
                user.setPowerReviewer(true);
                userRepository.save(user);
            }
        }

        // 리뷰 저장
        ReviewEntity savedReview = reviewRepository.save(review);

        // 리뷰 통계 업데이트
        reviewRepository.updateMovieStats();

        return savedReview;
    }

    public ReviewEntity updateReview(int reviewId, ReviewRequestDto reviewUpdateRequestDto) {
        // 리뷰 존재 여부 확인
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // 리뷰 정보 업데이트
        review.toBuilder()
                .rating(reviewUpdateRequestDto.getRating())
                .content(reviewUpdateRequestDto.getContent())
                .createDate(LocalDateTime.now())
                .build();

        System.out.println(review);

        // 변경된 리뷰 저장
        reviewRepository.updateMovieStats();

        return reviewRepository.save(review);
    }

    public void deleteReview(int reviewId) throws Exception {

        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();

        UserEntity user = userRepository.findById(id);

        if(review.getUser().getId() != user.getId()) {
            throw new Exception("사용자가 작성한 리뷰가 아닙니다.");
        }

        reviewRepository.updateMovieStats();

        reviewRepository.delete(review);
    }

    public ResponseEntity<List<ReviewResponseDto>> getPowerReviewList() {
        List<UserEntity> powerUsers = userRepository.findByPowerReviewerTrue();
        List<ReviewResponseDto> responses = new ArrayList<>();
        for (UserEntity user : powerUsers) {
            List<ReviewEntity> reviews = reviewRepository.findByUserIdOrderByCreateDateDesc(user.getUserId());
            boolean isHeart = reviewHeartRepository.existsByUserAndReview(user, reviews.get(0));
            ReviewResponseDto dto = ReviewResponseDto.builder()
                    .reviewId(reviews.get(0).getReviewId())
                    .movieId(reviews.get(0).getMovie().getMovieId())
                    .userId(user.getUserId())
                    .title(reviews.get(0).getMovie().getTitle())
                    .posterPath(reviews.get(0).getMovie().getPosterPath())
                    .nickname(user.getNickname())
                    .profileUrl(user.getProfile_url())
                    .content(reviews.get(0).getContent())
                    .rating(reviews.get(0).getRating())
                    .heartCount(reviews.get(0).getReviewHeartCount())
                    .createDate(reviews.get(0).getCreateDate())
                    .heart(isHeart)
                    .build();

            responses.add(dto);
        }
        return ResponseEntity.ok(responses);
    }
    public List<ResponseGraph> getUserGraph(int userId) throws Exception {

        UserEntity user = userRepository.findByUserId(userId);

        if(user == null) {
            throw new Exception("존재하지 않는 사용자입니다.");
        }

        ResponseUserReviewGraph graph = reviewRepository.getRatingCounts(userId);

        List<ResponseGraph> result = new ArrayList<>();

        result.add(new ResponseGraph("1점", graph.getOneStar()));
        result.add(new ResponseGraph("2점", graph.getTwoStar()));
        result.add(new ResponseGraph("3점", graph.getThreeStar()));
        result.add(new ResponseGraph("4점", graph.getFourStar()));
        result.add(new ResponseGraph("5점", graph.getFiveStar()));

        return result;
    }

    public List<ResponseUserReviewList> getUserReviewList(int userId) throws Exception {
        UserEntity user = userRepository.findByUserId(userId);

        if(user == null) {
            throw new Exception("존재하지 않는 사용자입니다.");
        }

        List<ResponseUserReviewList> result = reviewRepository.findUserReviewsByUserId(userId);

        return result;
    }

    public ResponseUserReviewList getReviewDetail(int reviewId) throws Exception {
        ResponseUserReviewList result = reviewRepository.findReviewDetail(reviewId);

        if(result == null) {
            throw new Exception("존재하지 않는 리뷰입니다.");
        }

        return result;
    }

    public ResponseReviewDetail editReview(ResponseReviewDetail editedReview) throws Exception {
        ReviewEntity before = reviewRepository.findByReviewId(editedReview.getReviewId());

        if(before == null) {
            throw new Exception("존재하지 않는 리뷰입니다.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();

        UserEntity user = userRepository.findById(id);

        if(before.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("사용자가 작성한 리뷰가 아닙니다.");
        }

        ReviewEntity review = before.toBuilder()
                .rating(editedReview.getRating())
                .content(editedReview.getContent())
                .createDate(LocalDateTime.now())
                .build();

        ReviewEntity after = reviewRepository.save(review);

        ResponseReviewDetail result = new ResponseReviewDetail(after);
        // 리뷰 통계 업데이트
        reviewRepository.updateMovieStats();

        return result;
    }

    public List<ResponseReviewPoster> getPosterList(int userId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();

        UserEntity user = userRepository.findById(id);

        if(user == null) {
            throw new Exception("존재하지 않는 사용자입니다.");
        }

        List<ReviewEntity> list = reviewRepository.findPosterByUserId(userId);
        int size = list.size();
        List<ResponseReviewPoster> result = new ArrayList<>();

        for(int i = 0; i < size; i++){
            result.add(new ResponseReviewPoster(list.get(i)));
        }

        return result;
    }

    public List<MovieReviewResponseDto> getLatestReviewsByMovieId(int movieId) {
        List<ReviewEntity> reviews = reviewRepository.findLatestReviewsByMovieId(movieId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();

        UserEntity user = userRepository.findById(id);

        List<MovieReviewResponseDto> responses = new ArrayList<>();
        for(ReviewEntity review : reviews){
            boolean isHeart = reviewHeartRepository.existsByUserAndReview(user, review);

            MovieReviewResponseDto dto = MovieReviewResponseDto.builder()
                    .reviewId(review.getReviewId())
                    .title(review.getMovie().getTitle()) // 영화 제목 가져오기
                    .posterPath(review.getMovie().getPosterPath()) // 영화 포스터 경로 추가
                    .nickname(review.getUser().getNickname())
                    .profileUrl(review.getUser().getProfile_url())
                    .rating(review.getRating())
                    .description(review.getDescription())
                    .userId(review.getUser().getUserId()) // 리뷰 작성자의 userId 추가
                    .content(review.getContent())
                    .createDate(review.getCreateDate())
                    .heartCount(review.getReviewHeartCount())
                    .heart(isHeart)
                    .build();
            responses.add(dto);
        }

        return responses;
    }

    public List<MovieReviewResponseDto> getFavoriteReviewsByMovieId(int movieId) {
        List<ReviewEntity> reviews = reviewRepository.findFavoriteReviewsByMovieId(movieId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();

        UserEntity user = userRepository.findById(id);

        List<MovieReviewResponseDto> responses = new ArrayList<>();
        for(ReviewEntity review : reviews){
            boolean isHeart = reviewHeartRepository.existsByUserAndReview(user, review);

            MovieReviewResponseDto dto = MovieReviewResponseDto.builder()
                    .reviewId(review.getReviewId())
                    .title(review.getMovie().getTitle()) // 영화 제목 가져오기
                    .posterPath(review.getMovie().getPosterPath()) // 영화 포스터 경로 추가
                    .nickname(review.getUser().getNickname())
                    .profileUrl(review.getUser().getProfile_url())
                    .userId(review.getUser().getUserId()) // 리뷰 작성자의 userId 추가
                    .rating(review.getRating())
                    .description(review.getDescription())
                    .content(review.getContent())
                    .createDate(review.getCreateDate())
                    .heartCount(review.getReviewHeartCount())
                    .heart(isHeart)
                    .build();
            responses.add(dto);
        }

        return responses;
    }

    public String getPreference(String userId) {
        Pageable top10 = PageRequest.of(0, 10);
        List<MovieEntity> movies = reviewRepository.findTop10(userId, top10);

        if(movies.isEmpty()) {
            return null;
        }

        String result = "내가 선호하는 영화들과 관련된 키워드 8개를 보여주려고 하는데, " +
                "내가 선호하는 영화 리스트를 줄테니까 그거보고 키워드 8개를 만들어줄래?" +
                "키워드는 장르 말고 보낸 영화들과 관련된 형용사 4개, 단어 4개로 해줘" +
                " 선호하는 영화는";

        for(MovieEntity movie : movies) {
            result += movie.getTitle() + ", ";
        }

        result += " 이렇게야. 응답은 json 형태로 줄래? 키값은 keyword1, keyword2 이런식으로 보내줘";

        return result;
    }
}