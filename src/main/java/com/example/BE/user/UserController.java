package com.example.BE.user;

import com.example.BE.actor.dto.RecommendedActor;
import com.example.BE.user.dto.EditedUserRequestDto;
import com.example.BE.user.dto.EditedUserResponseDto;
import com.example.BE.user.dto.ResponseUserInfo;
import com.example.BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.BE.review.ReviewEntity;
import com.example.BE.review.dto.response.SimpleReviewResponseDTO;
import com.example.BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinewall/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/edit/{userId}")
    public ResponseEntity editUser(@PathVariable("userId") int userId, @ModelAttribute EditedUserRequestDto requestDto) {

        try{
            EditedUserResponseDto responseDto = userService.editUser(userId, requestDto);
            return ResponseEntity.ok(responseDto);
        } catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(UnsupportedOperationException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }

    @GetMapping("/info")
    public ResponseEntity getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getName();

        ResponseUserInfo user = userService.getUser(id);

        return ResponseEntity.ok(user);
    }


    @GetMapping("/{userId}/reviews/latest")
    public ResponseEntity<List<SimpleReviewResponseDTO>> getUserLatestReviews(@PathVariable int userId) {
        List<SimpleReviewResponseDTO> reviews = userService.getUserLatestReviews(userId);
        return ResponseEntity.ok(reviews);
    }

//    @PostMapping("/kakaoAlert")
}
