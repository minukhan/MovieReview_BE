package com.example.BE.survey.service;


import com.example.BE.survey.SurveyEntity;
import org.springframework.http.ResponseEntity;

public interface SurveyService {
    public void submit_survey(SurveyEntity surveyEntity);
}
