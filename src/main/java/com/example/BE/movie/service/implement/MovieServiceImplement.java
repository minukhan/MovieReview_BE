package com.example.BE.movie.service.implement;

import com.example.BE.movie.MovieEntity;
import com.example.BE.movie.MovieRepository;
import com.example.BE.movie.dto.response.MovieResponseDto;
import com.example.BE.movie.dto.response.TeaserResponseDto;
import com.example.BE.movie.service.MovieService;
import com.example.BE.movie_vote.MovieVoteEntity;
import com.example.BE.movie_vote.MovieVoteRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImplement implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieVoteRepository movieVoteRepository;
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
}
