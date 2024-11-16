package com.autoever.cinewall.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinewall/movie")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final MovieService movieService;

    @GetMapping("/{movieId}/favorite")
    public ResponseEntity isMovieFavorite(@PathVariable int movieId) {
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

}
