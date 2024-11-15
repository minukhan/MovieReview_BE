package com.autoever.cinewall.favorite;

import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.movie.MovieRepository;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public FavoriteEntity addFavorite(int movieId) {
        // 사용자 조회
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();  // 인증된 사용자의 username (id) 가져오기

        // username을 통해 UserEntity 조회
        UserEntity user = userRepository.findById(id);

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

    public void removeFavorite(int movieId) {

        // 사용자 조회
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();  // 인증된 사용자의 username (id) 가져오기

        // username을 통해 UserEntity 조회
        UserEntity user = userRepository.findById(id);

        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        FavoriteEntity favorite = favoriteRepository.findByUserAndMovie(user, movie)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }

}
