package com.autoever.cinewall.recommend.service;

import com.autoever.cinewall.recommend.RecommendEntity;
import com.autoever.cinewall.recommend.dto.response.RecommendResponseDto;
import com.autoever.cinewall.user.UserEntity;
import org.springframework.http.ResponseEntity;

public interface RecommendService {
    public void submit_recommend(RecommendEntity recommend);
    public ResponseEntity<RecommendResponseDto> getHashtag(UserEntity user);
}
