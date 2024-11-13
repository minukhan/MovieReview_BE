package com.example.BE.favorite;


import com.example.BE.movie.MovieEntity;
import com.example.BE.movie.MovieRepository;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public FavoriteEntity addFavorite(int userId, int movieId) {
        // 사용자 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 영화 조회
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // 찜 목록에 이미 존재하는지 확인
        if (favoriteRepository.existsByUserAndMovie(user, movie)) {
            throw new RuntimeException("Movie is already in favorites");
        }

        // 찜 등록
        FavoriteEntity favorite = FavoriteEntity.builder()
                .user(user)
                .movie(movie)
                .build();

        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(int userId, int movieId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        FavoriteEntity favorite = favoriteRepository.findByUserAndMovie(user, movie)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }

}
