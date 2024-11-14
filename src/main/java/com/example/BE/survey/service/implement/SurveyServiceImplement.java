package com.example.BE.survey.service.implement;

import com.amazonaws.Response;
import com.example.BE.recommend.RecommendEntity;
import com.example.BE.recommend.RecommendRepository;
import com.example.BE.survey.SurveyEntity;
import com.example.BE.survey.SurveyRepository;
import com.example.BE.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyServiceImplement implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final RecommendRepository recommendRepository;

    public void submit_survey(SurveyEntity surveyEntity){
        surveyRepository.save(surveyEntity);
        return;
    }

}
