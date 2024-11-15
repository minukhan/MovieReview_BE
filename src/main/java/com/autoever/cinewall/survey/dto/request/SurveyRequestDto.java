package com.autoever.cinewall.survey.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SurveyRequestDto {
    private List<Integer> genres =  new ArrayList<>();
}
