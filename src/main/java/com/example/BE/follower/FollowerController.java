package com.example.BE.follower;

import com.example.BE.auth.provider.JwtProvider;
import com.example.BE.follower.dto.response.FollowerResponseDto;
import com.example.BE.follower.dto.response.FollowingResponseDto;
import com.example.BE.follower.service.FollowerService;
import com.example.BE.user.UserEntity;
import com.example.BE.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
            }
        }else{
            System.out.println("cookies is null");
        }


        // 1. Cookie에서 token 추출
        String token = jwtProvider.getTokenFromCookies(request);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. JwtProvider를 사용해 userId 추출
        String id = jwtProvider.validate(token);
        System.out.println(id);
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3. userId로 DB에서 사용자 정보 조회
        UserEntity user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        int user_id = user.getUserId();
        System.out.println(user_id);
        return ResponseEntity.ok(followerService.getFollowingList(user_id));
    }

    @GetMapping("/follower-list")
    public ResponseEntity<? super List<FollowerResponseDto>> getFollowerList(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
            }
        }else{
            System.out.println("cookies is null");
        }


        // 1. Cookie에서 token 추출
        String token = jwtProvider.getTokenFromCookies(request);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. JwtProvider를 사용해 userId 추출
        String id = jwtProvider.validate(token);
        System.out.println(id);
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3. userId로 DB에서 사용자 정보 조회
        UserEntity user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        int user_id = user.getUserId();
        System.out.println(user_id);
        return ResponseEntity.ok(followerService.getFollowerList(user_id));
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> follow(
            HttpServletRequest request,
            @RequestParam("to_user_id") int to_user_id
    ) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
            }
        }else{
            System.out.println("cookies is null");
        }


        // 1. Cookie에서 token 추출
        String token = jwtProvider.getTokenFromCookies(request);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. JwtProvider를 사용해 userId 추출
        String id = jwtProvider.validate(token);
        System.out.println(id);
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3. userId로 DB에서 사용자 정보 조회
        UserEntity user = userService.findById(id);
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
