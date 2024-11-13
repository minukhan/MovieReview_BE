package com.example.BE.survey;


import com.example.BE.auth.provider.JwtProvider;
import com.example.BE.genre.GenreEntity;
import com.example.BE.genre.dto.response.SurveyResponseDto;
import com.example.BE.genre.service.GenreService;
import com.example.BE.survey.dto.request.SurveyRequestDto;
import com.example.BE.survey.service.SurveyService;
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
@RequestMapping("/cinewall/survey")
public class SurveyController {
    private final GenreService genreService;
    private final SurveyService surveyService;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @GetMapping("/recommend")
    public ResponseEntity<? super List<SurveyResponseDto>> getSurveyList(){
        return genreService.getSurveyList();
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submit_survey(
            HttpServletRequest request,
            @RequestBody SurveyRequestDto requestDto){
        List<Integer> genres = requestDto.getGenres();

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

        for(int genreId : genres){
            GenreEntity genre = genreService.getGenreById(genreId);
            SurveyEntity survey = SurveyEntity.builder()
                    .user(user)
                    .genre(genre)
                    .build();
            surveyService.submit_survey(survey);
        }

        user.setSurvey(true);
        userService.updateUser(user);
        return ResponseEntity.ok().build();

    }
}
