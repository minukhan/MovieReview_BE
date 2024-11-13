package com.example.BE.survey;


import com.example.BE.follower.dto.response.FollowerResponseDto;
import com.example.BE.genre.dto.response.SurveyResponseDto;
import com.example.BE.genre.service.GenreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinewall/survey")
public class ServeyController {
    private final GenreService genreService;

    @GetMapping("/recommend")
    public ResponseEntity<? super List<SurveyResponseDto>> getSurveyList(){
        return genreService.getSurveyList();
    }
}
