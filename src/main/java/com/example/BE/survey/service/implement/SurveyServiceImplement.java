package com.example.BE.survey.service.implement;

import com.example.BE.genre.GenreEntity;
import com.example.BE.recommend.RecommendEntity;
import com.example.BE.recommend.RecommendRepository;
import com.example.BE.survey.SurveyEntity;
import com.example.BE.survey.SurveyRepository;
import com.example.BE.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyServiceImplement implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final RecommendRepository recommendRepository;

    public void submit_survey(SurveyEntity surveyEntity){
        surveyRepository.save(surveyEntity);
        List<GenreEntity> genres = surveyRepository.findGenresByUserId(surveyEntity.getUser().getUserId());
        RecommendEntity recommend = RecommendEntity.builder()
                .genres(genres)
                .user(surveyEntity.getUser())
                .build();
        recommendRepository.save(recommend);
        return;
    }

}
