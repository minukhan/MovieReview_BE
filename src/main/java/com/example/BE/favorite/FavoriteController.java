package com.example.BE.favorite;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinewall/movie")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{movieId}/favorite")
    public ResponseEntity<String> addFavorite(@PathVariable int movieId, @RequestParam int userId) {
        favoriteService.addFavorite(userId, movieId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Movie added to favorites successfully");
    }

    @DeleteMapping("/{movieId}/favorite")
    public ResponseEntity<String> removeFavorite(@PathVariable int movieId, @RequestParam int userId) {
        favoriteService.removeFavorite(userId, movieId);
        return ResponseEntity.ok("Movie removed from favorites successfully");
    }

}
