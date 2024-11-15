package com.example.BE.follower;

import com.example.BE.auth.provider.JwtProvider;
import com.example.BE.follower.dto.response.FollowerResponseDto;
import com.example.BE.follower.dto.response.FollowingResponseDto;
import com.example.BE.follower.service.FollowerService;
import com.example.BE.user.UserEntity;
import com.example.BE.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinewall/follow")
public class FollowerController {
    private final FollowerService followerService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @GetMapping("/following-list")
    public ResponseEntity<? super List<FollowingResponseDto>> getFollowingList(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity user = userService.findById(userName);
        int user_id = user.getUserId();
        return ResponseEntity.ok(followerService.getFollowingList(user_id));
    }

    @GetMapping("/follower-list")
    public ResponseEntity<? super List<FollowerResponseDto>> getFollowerList(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity user = userService.findById(userName);
        int user_id = user.getUserId();
        return ResponseEntity.ok(followerService.getFollowerList(user_id));
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> follow(
            HttpServletRequest request,
            @RequestParam("to_user_id") int to_user_id
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity user = userService.findById(userName);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserEntity to_user = userService.findByUserId(to_user_id);
        return followerService.add_follower(user, to_user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> unfollow(
            @RequestParam("follower_id") int follower_id
    ){
        return followerService.remove_follower(follower_id);
    }
}
