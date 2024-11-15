package com.autoever.cinewall.recommend.service.implement;

import com.autoever.cinewall.recommend.RecommendEntity;
import com.autoever.cinewall.recommend.RecommendRepository;
import com.autoever.cinewall.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendServiceImplement implements RecommendService {
    private final RecommendRepository recommendRepository;
    public void submit_recommend(RecommendEntity recommend) {
        recommendRepository.save(recommend);
    }
}
