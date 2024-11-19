package com.autoever.cinewall.survey.service.implement;

import com.autoever.cinewall.genre.GenreEntity;
import com.autoever.cinewall.recommend.RecommendEntity;
import com.autoever.cinewall.recommend.RecommendRepository;
import com.autoever.cinewall.survey.SurveyEntity;
import com.autoever.cinewall.survey.SurveyRepository;
import com.autoever.cinewall.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
