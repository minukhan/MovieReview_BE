package com.example.BE.movie;

//import com.example.BE.actor.ActorService;
//import com.example.BE.crew.CrewService;
//import com.example.BE.genre.GenreService;
import com.example.BE.auth.provider.JwtProvider;
import com.example.BE.movie.dto.response.MovieResponseDto;
import com.example.BE.movie.dto.response.TeaserResponseDto;
import com.example.BE.movie.service.MovieService;
import com.example.BE.user.UserEntity;
import com.example.BE.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/cinewall/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @GetMapping("/trailer")
    public ResponseEntity<List<TeaserResponseDto>> teaser() {
        return movieService.getTrailerList();
    }

    @GetMapping("/latest")
    public ResponseEntity<List<MovieResponseDto>> teaserLatest(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = 0;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
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
            user = userService.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            user = null;
        }

        if(user != null) user_id = user.getUserId();

        return movieService.getLatestMovieList(user_id);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieResponseDto>> popularity(HttpServletRequest request) {
        UserEntity user = null;
        int user_id = 0;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if("accessToken".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    jwtProvider.getUserRole(cookie.getValue());
                }
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
            user = userService.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            user = null;
        }

        if(user != null) user_id = user.getUserId();
        return movieService.getPopularList(user_id);
    }
}
