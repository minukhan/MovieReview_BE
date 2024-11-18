package com.autoever.cinewall.favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.autoever.cinewall.movie.service.MovieService;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/cinewall/movieFavorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final MovieService movieService;

    @GetMapping("/{movieId}/isFavorite")
    public ResponseEntity isMovieFavorite(@PathVariable("movieId") int movieId) {
        boolean result = movieService.isMovieFavorite(movieId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{movieId}/favorite")
    public ResponseEntity<String> addFavorite(@PathVariable int movieId) {
        favoriteService.addFavorite(movieId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Movie added to favorites successfully");
    }

    @DeleteMapping("/{movieId}/favorite")
    public ResponseEntity<String> removeFavorite(@PathVariable int movieId) {
        favoriteService.removeFavorite(movieId);
        return ResponseEntity.ok("Movie removed from favorites successfully");
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getFavoriteCountByUser() {
        try {
            int favoriteCount = favoriteService.getFavoriteCountByUserId();
            return ResponseEntity.ok(favoriteCount);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0); // 사용자 정보가 없을 때
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0); // 기타 에러 발생 시
        }
    }
}
