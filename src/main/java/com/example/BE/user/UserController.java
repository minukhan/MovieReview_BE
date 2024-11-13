package com.example.BE.user;


import com.example.BE.review.ReviewEntity;
import com.example.BE.review.dto.response.SimpleReviewResponseDTO;
import com.example.BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinewall/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/reviews/latest")
    public ResponseEntity<List<SimpleReviewResponseDTO>> getUserLatestReviews(@PathVariable int userId) {
        List<SimpleReviewResponseDTO> reviews = userService.getUserLatestReviews(userId);
        return ResponseEntity.ok(reviews);
    }

}
