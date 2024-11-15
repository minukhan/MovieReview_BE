package com.example.BE.survey;


import com.example.BE.auth.provider.JwtProvider;
import com.example.BE.genre.GenreEntity;
import com.example.BE.genre.dto.response.SurveyResponseDto;
import com.example.BE.genre.service.GenreService;
import com.example.BE.recommend.RecommendEntity;
import com.example.BE.recommend.service.RecommendService;
import com.example.BE.survey.dto.request.SurveyRequestDto;
import com.example.BE.survey.service.SurveyService;
import com.example.BE.user.UserEntity;
import com.example.BE.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinewall/survey")
public class SurveyController {
    private final GenreService genreService;
    private final SurveyService surveyService;
    private final RecommendService recommendService;
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        user = userService.findById(userName);

        if(user != null) user_id = user.getUserId();

        List<GenreEntity> genreEntities = new ArrayList<>();

        for(int genreId : genres){
            GenreEntity genre = genreService.getGenreById(genreId);
            genreEntities.add(genre);
            SurveyEntity survey = SurveyEntity.builder()
                    .user(user)
                    .genre(genre)
                    .build();
            surveyService.submit_survey(survey);
        }

        RecommendEntity recommend = RecommendEntity.builder()
                .user(user)
                .genres(genreEntities)
                .build();
        recommendService.submit_recommend(recommend);

        user.setSurvey(true);
        userService.updateUser(user);
        return ResponseEntity.ok().build();

    }
}
