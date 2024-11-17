package com.autoever.cinewall.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autoever.cinewall.movie.service.MovieService;

import java.util.Map;

@RestController
@RequestMapping("/cinewall/movie")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final MovieService movieService;

    @GetMapping("/{movieId}/isFavorite")
    public ResponseEntity isMovieFavorite(@PathVariable("movieId") int movieId,
                                          @RequestBody(required = false) Map<String, Integer> json) {

        if(json == null) {
            return ResponseEntity.ok(false);
        } else{
            Integer userId = json.get("userId");
            boolean result = movieService.isMovieFavorite(userId, movieId);
            return ResponseEntity.ok(result);
        }
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

}
